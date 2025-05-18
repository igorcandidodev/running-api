package com.runningapi.runningapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.runningapi.runningapi.dto.PromptVariablesDto;
import com.runningapi.runningapi.dto.openai.ChatGptResponseDto;
import com.runningapi.runningapi.dto.strava.response.activity.StravaActivityResponse;
import com.runningapi.runningapi.model.enums.StatusActivity;
import com.runningapi.runningapi.exceptions.ChatGptResponseNotFound;
import com.runningapi.runningapi.exceptions.ChatGptResponseProcessingException;
import com.runningapi.runningapi.exceptions.UserNotFound;
import com.runningapi.runningapi.exceptions.UserPromptNotFound;
import com.runningapi.runningapi.model.*;
import com.runningapi.runningapi.model.strava.MapStrava;
import com.runningapi.runningapi.repository.*;
import com.runningapi.runningapi.utils.DateConverter;
import org.hibernate.ObjectNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TrainingService {

    private final TrainingRepository trainingRepository;
    private final TrainingPerformedRepository trainingPerformedRepository;
    private final ChatGptService chatGptService;
    private final PromptTemplateService promptTemplateService;
    private final ObjectiveRepository objectiveRepository;
    private final PhysicalActivityRepository physicalActivityRepository;
    private final PhysicalLimitationRepository physicalLimitationRepository;
    private final RunningActivityRepository runningActivityRepository;
    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;
    private final MapStravaRepository mapStravaRepository;

    public TrainingService(TrainingRepository trainingRepository,
                           TrainingPerformedRepository trainingPerformedRepository,
                           ChatGptService chatGptService,
                           PromptTemplateService promptTemplateService,
                           ObjectiveRepository objectiveRepository,
                           PhysicalActivityRepository physicalActivityRepository,
                           PhysicalLimitationRepository physicalLimitationRepository,
                           RunningActivityRepository runningActivityRepository,
                           ObjectMapper objectMapper, UserRepository userRepository, MapStravaRepository mapStravaRepository) {
        this.trainingRepository = trainingRepository;
        this.trainingPerformedRepository = trainingPerformedRepository;
        this.chatGptService = chatGptService;
        this.promptTemplateService = promptTemplateService;
        this.objectiveRepository = objectiveRepository;
        this.physicalActivityRepository = physicalActivityRepository;
        this.physicalLimitationRepository = physicalLimitationRepository;
        this.runningActivityRepository = runningActivityRepository;
        this.objectMapper = objectMapper;
        this.userRepository = userRepository;
        this.mapStravaRepository = mapStravaRepository;
    }

    public List<Training> createTrainings(Long promptId) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> optionalUser = userRepository.findByEmail(userDetails.getUsername());
        if (optionalUser.isPresent()) {
            var user = optionalUser.get();

            Objective objective = objectiveRepository.findLatestByUserId(user.getId())
                    .orElseThrow(() -> new RuntimeException("Objective not found"));
            PhysicalActivity physicalActivity = physicalActivityRepository.findByUserId(user.getId())
                    .orElseThrow(() -> new RuntimeException("Physical Activity not found"));
            PhysicalLimitation physicalLimitation = physicalLimitationRepository.findByUserId(user.getId())
                    .orElseThrow(() -> new RuntimeException("Physical Limitation not found"));

            PromptVariablesDto promptVariablesDto = new PromptVariablesDto(
                    objective,
                    physicalActivity,
                    physicalLimitation,
                    runningActivityRepository,
                    user
            );

            String systemPrompt = promptTemplateService.getSystemPrompt(promptId);
            String updatedUserPrompt = promptTemplateService.getUpdatedUserPrompt(promptId, promptVariablesDto);
            if (updatedUserPrompt == null || updatedUserPrompt.isEmpty()) {
                throw new UserPromptNotFound();
            }

            // TODO: Pay assinature on OpenAi to use the chatGptService

            String response = chatGptService.sendPrompt(systemPrompt, updatedUserPrompt);

            if (response == null || response.isEmpty()) {
                throw new ChatGptResponseNotFound();
            }
            return processChatGptResponse(response, objective);
        } else {
            throw new UserNotFound(userDetails.getUsername());
        }
    }

    private List<Training> processChatGptResponse(String response, Objective objective) {
        try {
            ChatGptResponseDto responseDto = objectMapper.readValue(response, ChatGptResponseDto.class);

            List<Training> trainings = responseDto.weeks().stream()
                    .flatMap(weekDto ->
                            weekDto.trainings().stream()
                                    .map(trainingDto -> {
                                        Training training = new Training(trainingDto);
                                        training.setWeekNumber(weekDto.number());
                                        training.setObjective(objective);
                                        return training;
                                    })
                    )
                    .collect(Collectors.toList());

            return trainingRepository.saveAll(trainings);

        } catch (Exception e) {
            throw new ChatGptResponseProcessingException(e.getMessage(), e);
        }
    }

    public Training findTrainingByDateAndUserId(String date, Long userId) {
        return trainingRepository.findByDateAndUserId(date, userId).orElseThrow(() -> new RuntimeException("Training not found"));
    }

    public Training saveTraining(Training training) {
        return trainingRepository.save(training);
    }

    public void linkTrainingWithTrainingPerformed(StravaActivityResponse training, Long idUser) {

        var trainingTarget = findTrainingByDateAndUserId(DateConverter.convertToString(training.startDate(), "DD/MM/YYYY"),
                idUser);

        if (trainingTarget != null) {
            var trainingPerformed = getTrainingPerformed(training, trainingTarget);
            var mapStrava = getMapStrava(training);

            if (mapStrava != null) {
                mapStravaRepository.save(mapStrava);

                trainingPerformed.setMapStrava(mapStrava);
            }

            trainingPerformedRepository.save(trainingPerformed);

            trainingTarget.setTrainingPerformed(trainingPerformed);
            trainingTarget.setStatusActivity(StatusActivity.COMPLETED);

            saveTraining(trainingTarget);

        }
    }

    private TrainingPerformed getTrainingPerformed(StravaActivityResponse training, Training trainingTarget) {
        var trainingPerformed = new TrainingPerformed();

        trainingPerformed.setTitle(trainingTarget.getTitle());
        trainingPerformed.setDescription(trainingTarget.getDescription());
        trainingPerformed.setDate(training.startDate());
        trainingPerformed.setDistance(training.distance());
        trainingPerformed.setTraining(trainingTarget);
        trainingPerformed.setIdStrava(training.id());
        return trainingPerformed;
    }

    private MapStrava getMapStrava(StravaActivityResponse training) {
        if(training.map() == null) {
            return null;
        }
        MapStrava mapStrava = new MapStrava();

        mapStrava.setIdStrava(training.map().id());
        mapStrava.setSummaryPolyline(training.map().summaryPolyline());
        mapStrava.setResourceState(training.map().resourceState());
        mapStrava.setPolyline(training.map().polyline());

        return mapStrava;
    }

    public Training findTrainingByStravaId(Long stravaId) {
        return trainingRepository.findByIdStrava(stravaId)
                .orElseThrow(() -> new RuntimeException("Training performed not found"));
    }

    public List<Training> getAllTrainings() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return trainingRepository.findAllTrainingByUserEmail(userDetails.getUsername());
    }

    public List<Training> getAllTrainingsForPeriod(LocalDate startDate, LocalDate endDate) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return trainingRepository.findAllForPeriodByUserEmail(userDetails.getUsername(), startDate, (endDate != null ? endDate : LocalDate.now()));
    }

    public Training findTrainingById(Long id) {
        return trainingRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Training not found", id));
    }
}
