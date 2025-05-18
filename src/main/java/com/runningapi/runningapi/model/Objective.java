package com.runningapi.runningapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.runningapi.runningapi.dto.ObjectiveDto;
import com.runningapi.runningapi.model.enums.StatusObjective;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "OBJECTIVES")
public class Objective implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false)
    private double targetDistance;

    @Column(nullable = false, columnDefinition = "BIGINT")
    private Duration targetTime;

    @Column(nullable = false)
    private LocalDate targetDate;

    @Column(nullable = false)
    private boolean isFirstTimeExercising;

    @Column(nullable = false)
    private List<String> availableTrainingDays;

    @Enumerated(EnumType.STRING)
    private StatusObjective status;

    @OneToMany(mappedBy = "objective", cascade = CascadeType.ALL)
    private List<Training> trainings;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;


    public Objective() {

    }

    public Objective(ObjectiveDto objectiveDto, User user) {
        this.title = objectiveDto.title();
        this.targetDistance = objectiveDto.targetDistance();
        this.targetTime = objectiveDto.targetTime();
        this.targetDate = objectiveDto.targetDate();
        this.isFirstTimeExercising = objectiveDto.isFirstTimeExercising();
        this.availableTrainingDays = objectiveDto.availableTrainingDays();
        this.user = user;
        this.status = StatusObjective.ACTIVE;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getTargetDistance() {
        return targetDistance;
    }

    public void setTargetDistance(double targetDistance) {
        this.targetDistance = targetDistance;
    }

    public Duration getTargetTime() {
        return targetTime;
    }

    public void setTargetTime(Duration targetTime) {
        this.targetTime = targetTime;
    }

    public LocalDate getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(LocalDate targetDate) {
        this.targetDate = targetDate;
    }

    public boolean isFirstTimeExercising() {
        return isFirstTimeExercising;
    }

    public void setFirstTimeExercising(boolean firstTimeExercising) {
        isFirstTimeExercising = firstTimeExercising;
    }

    public List<String> getAvailableTrainingDays() {
        return availableTrainingDays;
    }

    public void setAvailableTrainingDays(List<String> availableTrainingDays) {
        this.availableTrainingDays = availableTrainingDays;
    }

    public List<Training> getTrainings() {
        return trainings;
    }

    public void setTrainings(List<Training> trainings) {
        this.trainings = trainings;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public StatusObjective getStatus() {
        return status;
    }

    public void setStatus(StatusObjective status) {
        this.status = status;
    }
}
