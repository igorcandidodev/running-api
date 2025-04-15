package com.runningapi.runningapi.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZonedDateTime;

@Entity(name = "TRAININGS_PERFORMEDS")
public class TrainingPerformed implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private Long idStrava;

    private String description;

    @Column(nullable = false)
    private ZonedDateTime date;

    private Double distance;

    private Double movingTime;

    private Double elapsedTime;

    private Double totalElevationGain;

    private Double averageSpeed;

    private Double calories;

    @OneToOne
    @JoinColumn(name = "training_id")
    private Training training;

    @ManyToOne
    @JoinColumn(name = "objective_id", nullable = false)
    private Objective objective;

    public TrainingPerformed() {

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public Training getTraining() {
        return training;
    }

    public void setTraining(Training training) {
        this.training = training;
    }

    public Objective getObjective() {
        return objective;
    }

    public void setObjective(Objective objective) {
        this.objective = objective;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Double getMovingTime() {
        return movingTime;
    }

    public void setMovingTime(Double movingTime) {
        this.movingTime = movingTime;
    }

    public Double getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(Double elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public Double getTotalElevationGain() {
        return totalElevationGain;
    }

    public void setTotalElevationGain(Double totalElevationGain) {
        this.totalElevationGain = totalElevationGain;
    }

    public Double getAverageSpeed() {
        return averageSpeed;
    }

    public void setAverageSpeed(Double averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    public Double getCalories() {
        return calories;
    }

    public void setCalories(Double calories) {
        this.calories = calories;
    }

    public Long getIdStrava() {
        return idStrava;
    }

    public void setIdStrava(Long idStrava) {
        this.idStrava = idStrava;
    }
}
