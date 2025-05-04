package com.runningapi.runningapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.runningapi.runningapi.dto.PromptVariablesDto;
import com.runningapi.runningapi.dto.openai.ChatGptResponseDto;
import com.runningapi.runningapi.dto.strava.response.activity.StravaActivityResponse;
import com.runningapi.runningapi.enums.StatusActivity;
import com.runningapi.runningapi.exceptions.ChatGptResponseNotFound;
import com.runningapi.runningapi.exceptions.ChatGptResponseProcessingException;
import com.runningapi.runningapi.exceptions.UserPromptNotFound;
import com.runningapi.runningapi.model.*;
import com.runningapi.runningapi.repository.*;
import com.runningapi.runningapi.utils.DateConverter;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public TrainingService(TrainingRepository trainingRepository,
                           TrainingPerformedRepository trainingPerformedRepository,
                           ChatGptService chatGptService,
                           PromptTemplateService promptTemplateService,
                           ObjectiveRepository objectiveRepository,
                           PhysicalActivityRepository physicalActivityRepository,
                           PhysicalLimitationRepository physicalLimitationRepository,
                           RunningActivityRepository runningActivityRepository,
                           ObjectMapper objectMapper) {
        this.trainingRepository = trainingRepository;
        this.trainingPerformedRepository = trainingPerformedRepository;
        this.chatGptService = chatGptService;
        this.promptTemplateService = promptTemplateService;
        this.objectiveRepository = objectiveRepository;
        this.physicalActivityRepository = physicalActivityRepository;
        this.physicalLimitationRepository = physicalLimitationRepository;
        this.runningActivityRepository = runningActivityRepository;
        this.objectMapper = objectMapper;
    }

    public List<Training> createTrainings(Long promptId, Long userId) {
        Objective objective = objectiveRepository.findLatestByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Objective not found"));
        PhysicalActivity physicalActivity = physicalActivityRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Physical Activity not found"));
        PhysicalLimitation physicalLimitation = physicalLimitationRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Physical Limitation not found"));

        PromptVariablesDto promptVariablesDto = new PromptVariablesDto(
                objective,
                physicalActivity,
                physicalLimitation,
                runningActivityRepository,
                userId
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
            var trainingPerformed = new TrainingPerformed();

            trainingPerformed.setTitle(trainingTarget.getTitle());
            trainingPerformed.setDescription(trainingTarget.getDescription());
            trainingPerformed.setDate(training.startDate());
            trainingPerformed.setDistance(training.distance());
            trainingPerformed.setTraining(trainingTarget);
            trainingPerformed.setObjective(trainingTarget.getObjective());
            trainingPerformed.setIdStrava(training.id());

            trainingPerformedRepository.save(trainingPerformed);

            trainingTarget.setTrainingPerformed(trainingPerformed);
            trainingTarget.setStatusActivity(StatusActivity.COMPLETED);

            saveTraining(trainingTarget);

        }
    }

    public Training findTrainingByStravaId(Long stravaId) {
        return trainingRepository.findByIdStrava(stravaId)
                .orElseThrow(() -> new RuntimeException("Training performed not found"));
    }
}
