package com.runningapi.runningapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.runningapi.runningapi.dto.PhysicalActivityDto;
import com.runningapi.runningapi.model.enums.Frequency;
import com.runningapi.runningapi.model.enums.SportActivity;
import jakarta.persistence.*;
import jakarta.validation.Valid;

import java.io.Serializable;

@Entity(name = "PHYSICAL_ACTIVITIES")
public class PhysicalActivity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(length = 15)
    private SportActivity sportActivity;
    @Enumerated(EnumType.STRING)
    @Column(length = 15)
    private Frequency frequency;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;

    public PhysicalActivity() {

    }

    public PhysicalActivity(@Valid PhysicalActivityDto physicalActivityDto) {
        this.sportActivity = physicalActivityDto.sportActivity();
        this.frequency = physicalActivityDto.frequency();
    }

    public PhysicalActivity(@Valid PhysicalActivityDto physicalActivityDto, User user) {
        this.sportActivity = physicalActivityDto.sportActivity();
        this.frequency = physicalActivityDto.frequency();
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SportActivity getSportActivity() {
        return sportActivity;
    }

    public void setSportActivity(SportActivity sportActivity) {
        this.sportActivity = sportActivity;
    }

    public Frequency getFrequency() {
        return frequency;
    }

    public void setFrequency(Frequency frequency) {
        this.frequency = frequency;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
