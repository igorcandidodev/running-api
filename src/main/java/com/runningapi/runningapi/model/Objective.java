package com.runningapi.runningapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

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
    @Column(nullable = false)
    private Duration targetTime;
    @Column(nullable = false)
    private LocalDate targetDate;
    @Column(nullable = false)
    private boolean isFirstTimeExercising;
    @Column(nullable = false)
    private Set<DayOfWeek> availableTrainingDays;
    @OneToMany(mappedBy = "objective", cascade = CascadeType.ALL)
    private List<Training> trainings;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;

    public Objective(Long id, String title, double targetDistance, Duration targetTime, LocalDate targetDate, boolean isFirstTimeExercising, Set<DayOfWeek> availableTrainingDays) {
        this.id = id;
        this.title = title;
        this.targetDistance = targetDistance;
        this.targetTime = targetTime;
        this.targetDate = targetDate;
        this.isFirstTimeExercising = isFirstTimeExercising;
        this.availableTrainingDays = availableTrainingDays;
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

    public Set<DayOfWeek> getAvailableTrainingDays() {
        return availableTrainingDays;
    }

    public void setAvailableTrainingDays(Set<DayOfWeek> availableTrainingDays) {
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
}
