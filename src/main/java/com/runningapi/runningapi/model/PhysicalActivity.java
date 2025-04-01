package com.runningapi.runningapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.runningapi.runningapi.enums.Frequency;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity(name = "PHYSICAL_ACTIVITIES")
public class PhysicalActivity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Frequency frequency;
    private String observation;
    @Column(nullable = false)
    private boolean feltPain;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;

    public PhysicalActivity(Long id, String name, Frequency frequency, String observation, boolean feltPain, User user) {
        this.id = id;
        this.name = name;
        this.frequency = frequency;
        this.observation = observation;
        this.feltPain = feltPain;
        this.user = user;
    }

    public PhysicalActivity() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Frequency getFrequency() {
        return frequency;
    }

    public void setFrequency(Frequency frequency) {
        this.frequency = frequency;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
