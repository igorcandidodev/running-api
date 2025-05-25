package com.runningapi.runningapi.service;

import com.runningapi.runningapi.dto.request.trainingperformed.TrainingPerformedRequestDto;
import com.runningapi.runningapi.exceptions.TrainingPerformedException;
import com.runningapi.runningapi.mapper.TrainingPerformedMapper;
import com.runningapi.runningapi.model.TrainingPerformed;
import com.runningapi.runningapi.model.enums.StatusActivity;
import com.runningapi.runningapi.repository.TrainingPerformedRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class TrainingPerformedService {

    private final TrainingPerformedRepository trainingPerformedRepository;
    private final TrainingService trainingService;

    private final TrainingPerformedMapper trainingPerformedMapper;

    public TrainingPerformedService(TrainingPerformedRepository trainingPerformedRepository, TrainingService trainingService, TrainingPerformedMapper trainingPerformedMapper) {
        this.trainingPerformedRepository = trainingPerformedRepository;
        this.trainingService = trainingService;
        this.trainingPerformedMapper = trainingPerformedMapper;
    }

    public TrainingPerformed findTrainingPerformedByStravaId(Long stravaId) {
        return trainingPerformedRepository.findByIdStrava(stravaId)
                .orElseThrow(() -> new RuntimeException("Training performed not found"));
    }

    public TrainingPerformed saveTraining(TrainingPerformed trainingPerformed) {
        return trainingPerformedRepository.save(trainingPerformed);
    }

    public TrainingPerformed createTrainingPerformed(Long idTraining, TrainingPerformedRequestDto trainingPerformedRequestDto) {
        var user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {
            var training = trainingService.findTrainingById(idTraining);
            if(training.getObjective().getUser().getEmail().equals(user.getUsername())) {
                var trainingPerformed = trainingPerformedMapper.toEntity(trainingPerformedRequestDto);
                trainingPerformed.setTraining(training);

                training.setStatusActivity(StatusActivity.COMPLETED);

                trainingService.saveTraining(training);

                return trainingPerformedRepository.save(trainingPerformed);

            } else {
                throw new TrainingPerformedException("Treino não pertence ao usuário autenticado.");
            }
        }
        catch (ObjectNotFoundException e) {
            throw new TrainingPerformedException("Treino informado não encontrado: " + e.getMessage());
        }
        catch (Exception e) {
            throw new TrainingPerformedException("Error creating training performed: " + e.getMessage());
        }
    }
}
