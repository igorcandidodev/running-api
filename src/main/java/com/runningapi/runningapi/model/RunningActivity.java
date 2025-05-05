package com.runningapi.runningapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.runningapi.runningapi.dto.RunningActivityDto;
import com.runningapi.runningapi.enums.Intensity;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;

@Entity(name = "RUNNING_ACTIVITIES")
public class RunningActivity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private double distanceCovered;
    @Column(nullable = false, columnDefinition = "BIGINT")
    private Duration timeSpent;
    private LocalDate date;
    @Column(nullable = false, length = 15)
    @Enumerated(EnumType.STRING)
    private Intensity intensity;
    @Column(nullable = false)
    private boolean feltTired;
    @Column(nullable = false)
    private boolean isBestResult;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;

    public RunningActivity(double distanceCovered, Duration timeSpent, LocalDate date, Intensity intensity, boolean feltTired, boolean isBestResult, User user) {
        this.distanceCovered = distanceCovered;
        this.timeSpent = timeSpent;
        this.date = date;
        this.intensity = intensity;
        this.feltTired = feltTired;
        this.isBestResult = isBestResult;
        this.user = user;
    }

    public RunningActivity() {

    }

    public RunningActivity(RunningActivityDto runningActivityDto, User user) {
        this.distanceCovered = runningActivityDto.distanceCovered();
        this.timeSpent = runningActivityDto.timeSpent();
        this.date = runningActivityDto.date();
        this.intensity = runningActivityDto.intensity();
        this.feltTired = runningActivityDto.feltTired();
        this.isBestResult = runningActivityDto.isBestResult();
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getDistanceCovered() {
        return distanceCovered;
    }

    public void setDistanceCovered(double distanceCovered) {
        this.distanceCovered = distanceCovered;
    }

    public Duration getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(Duration timeSpent) {
        this.timeSpent = timeSpent;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Intensity getIntensity() {
        return intensity;
    }

    public void setIntensity(Intensity intensity) {
        this.intensity = intensity;
    }

    public boolean isFeltTired() {
        return feltTired;
    }

    public void setFeltTired(boolean feltTired) {
        this.feltTired = feltTired;
    }

    public boolean isBestResult() {
        return isBestResult;
    }

    public void setBestResult(boolean bestResult) {
        isBestResult = bestResult;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
