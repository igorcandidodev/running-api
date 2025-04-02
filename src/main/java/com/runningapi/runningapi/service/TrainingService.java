package com.runningapi.runningapi.service;

import com.runningapi.runningapi.dto.strava.response.activity.StravaActivityResponse;
import com.runningapi.runningapi.enums.StatusActivity;
import com.runningapi.runningapi.model.Training;
import com.runningapi.runningapi.model.TrainingPerformed;
import com.runningapi.runningapi.repository.TrainingPerformedRepository;
import com.runningapi.runningapi.repository.TrainingRepository;
import com.runningapi.runningapi.utils.DataConverter;
import org.springframework.stereotype.Service;

@Service
public class TrainingService {

    private final TrainingRepository trainingRepository;


    private final TrainingPerformedRepository trainingPerformedRepository;


    public TrainingService(TrainingRepository trainingRepository, TrainingPerformedRepository trainingPerformedRepository) {
        this.trainingRepository = trainingRepository;
        this.trainingPerformedRepository = trainingPerformedRepository;
    }

    public Training findTrainingByDateAndUserId(String date, Long userId) {
        return trainingRepository.findByDateAndUserId(date, userId).orElseThrow(() -> new RuntimeException("Training not found"));
    }

    public Training saveTraining(Training training) {
        return trainingRepository.save(training);
    }

    public void linkTrainingWithTrainingPerformed(StravaActivityResponse training, Long idUser) {

        var trainingTarget = findTrainingByDateAndUserId(DataConverter.convertToString(training.startDate(), "DD/MM/YYYY"),
                idUser);

        if (trainingTarget != null) {
            var trainingPerformed = new TrainingPerformed();

            trainingPerformed.setTitle(trainingTarget.getTitle());
            trainingPerformed.setDescription(trainingTarget.getDescription());
            trainingPerformed.setDate(training.startDate());
            trainingPerformed.setDistance(training.distance());
            trainingPerformed.setTraining(trainingTarget);
            trainingPerformed.setObjective(trainingTarget.getObjective());

            trainingPerformedRepository.save(trainingPerformed);

            trainingTarget.setTrainingPerformed(trainingPerformed);
            trainingTarget.setStatusActivity(StatusActivity.COMPLETED);

            saveTraining(trainingTarget);

        }
    }
}
