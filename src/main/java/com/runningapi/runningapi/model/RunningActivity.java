package com.runningapi.runningapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.Duration;

@Entity(name = "RUNNING_ACTIVITIES")
public class RunningActivity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private double distanceCovered;
    @Column(nullable = false)
    private Duration timeSpent;
    private String observation;
    @Column(nullable = false)
    private boolean feltPain;
    @Column(nullable = false)
    private boolean isBestResult;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;

    public RunningActivity(Long id, double distanceCovered, Duration timeSpent, String observation, boolean feltPain, boolean isBestResult, User user) {
        this.id = id;
        this.distanceCovered = distanceCovered;
        this.timeSpent = timeSpent;
        this.observation = observation;
        this.feltPain = feltPain;
        this.isBestResult = isBestResult;
        this.user = user;
    }

    public RunningActivity() {

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

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public boolean isFeltPain() {
        return feltPain;
    }

    public void setFeltPain(boolean feltPain) {
        this.feltPain = feltPain;
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
