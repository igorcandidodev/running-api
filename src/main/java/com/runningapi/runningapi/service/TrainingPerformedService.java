package com.runningapi.runningapi.service;

import com.runningapi.runningapi.model.TrainingPerformed;
import com.runningapi.runningapi.repository.TrainingPerformedRepository;
import org.springframework.stereotype.Service;

@Service
public class TrainingPerformedService {

    private final TrainingPerformedRepository trainingPerformedRepository;

    public TrainingPerformedService(TrainingPerformedRepository trainingPerformedRepository) {
        this.trainingPerformedRepository = trainingPerformedRepository;
    }

    public TrainingPerformed findTrainingPerformedByStravaId(Long stravaId) {
        return trainingPerformedRepository.findByIdStrava(stravaId)
                .orElseThrow(() -> new RuntimeException("Training performed not found"));
    }

    public TrainingPerformed saveTraining(TrainingPerformed trainingPerformed) {
        return trainingPerformedRepository.save(trainingPerformed);
    }
}
