package com.runningapi.runningapi.dto;

import com.runningapi.runningapi.model.*;
import com.runningapi.runningapi.repository.RunningActivityRepository;

import java.time.Duration;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record PromptVariablesDto(Objective objective,
                                 PhysicalActivity physicalActivity,
                                 PhysicalLimitation physicalLimitation,
                                 RunningActivityRepository runningActivityRepository,
                                 User user) {

    private String formatDuration(Duration duration) {
        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;
        long seconds = duration.getSeconds() % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public Map<String, Object> promptVariables() {
        final String DEFAULT_TIME_FORMAT = "00:00:00";
        Map<String, Object> variables = new HashMap<>();

        var currentDate = LocalDate.now();
        List<RunningActivity> lastRuns = runningActivityRepository.findTop2ByUserIdOrderByDateDesc(user.getId());
        List<RunningActivity> bestRuns = runningActivityRepository.findTop2ByUserIdAndIsBestResultTrueOrderByDateDesc(user.getId());

        variables.put("targetDistance", objective.getTargetDistance());
        variables.put("targetDate", objective.getTargetDate());
        variables.put("targetTime", objective.getTargetTime() != null ? formatDuration(objective.getTargetTime()) : DEFAULT_TIME_FORMAT);
        variables.put("currentDate", currentDate);

        variables.put("lastRun1Distance", !lastRuns.isEmpty() ? lastRuns.get(0).getDistanceCovered() : 0);
        variables.put("lastRun1Time", !lastRuns.isEmpty() ? formatDuration(lastRuns.get(0).getTimeSpent()) : DEFAULT_TIME_FORMAT);
        variables.put("lastRun2Distance", lastRuns.size() > 1 ? lastRuns.get(1).getDistanceCovered() : 0);
        variables.put("lastRun2Time", lastRuns.size() > 1 ? formatDuration(lastRuns.get(1).getTimeSpent()) : DEFAULT_TIME_FORMAT);

        variables.put("bestRun1Distance", !bestRuns.isEmpty() ? bestRuns.get(0).getDistanceCovered() : 0);
        variables.put("bestRun1Time", !bestRuns.isEmpty() ? formatDuration(bestRuns.get(0).getTimeSpent()) : DEFAULT_TIME_FORMAT);
        variables.put("bestRun2Distance", bestRuns.size() > 1 ? bestRuns.get(1).getDistanceCovered() : 0);
        variables.put("bestRun2Time", bestRuns.size() > 1 ? formatDuration(bestRuns.get(1).getTimeSpent()) : DEFAULT_TIME_FORMAT);

        variables.put("sportName", physicalActivity.getSportActivity());
        variables.put("frequency", physicalActivity.getFrequency());

        variables.put("feltPain", physicalLimitation.isFeltPain() ? "Sim" : "Não");
        variables.put("painDescription", physicalLimitation.getDescription());

        variables.put("intensityLastRun1", lastRuns.getFirst().getIntensity());
        variables.put("intensityLastRun2", lastRuns.size() > 1 ? lastRuns.get(1).getIntensity() : 0);
        variables.put("intensityBestRun1", bestRuns.getFirst().getIntensity());
        variables.put("intensityBestRun2", bestRuns.size() > 1 ? bestRuns.get(1).getIntensity() : 0);

        variables.put("feltTiredLastRun1", lastRuns.getFirst().isFeltTired() ? "Sim" : "Não");
        variables.put("feltTiredLastRun2", lastRuns.size() > 1 && lastRuns.get(1).isFeltTired() ? "Sim" : "Não");
        variables.put("feltTiredBestRun1", bestRuns.getFirst().isFeltTired() ? "Sim" : "Não");
        variables.put("feltTiredBestRun2", bestRuns.size() > 1 && bestRuns.get(1).isFeltTired() ? "Sim" : "Não");

        variables.put("feltTiredLastRuns", lastRuns.getFirst().isFeltTired() ? "Sim" : "Não");
        variables.put("feltTiredBestRuns", bestRuns.getFirst().isFeltTired() ? "Sim" : "Não");

        variables.put("availableTrainingDays", objective.getAvailableTrainingDays());
        return variables;
    }
}