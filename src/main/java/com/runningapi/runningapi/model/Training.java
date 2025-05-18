package com.runningapi.runningapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.runningapi.runningapi.dto.TrainingDto;
import com.runningapi.runningapi.model.enums.StatusActivity;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Entity(name = "TRAININGS")
public class Training implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 1000)
    private String description;

    @Column(nullable = false)
    private Integer weekNumber;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private String weekDay;

    @Column(nullable = false, length = 15)
    @Enumerated(EnumType.STRING)
    private StatusActivity statusActivity = StatusActivity.PENDING;

    @OneToOne(mappedBy = "training", cascade = CascadeType.ALL)
    @JsonManagedReference
    private TrainingPerformed trainingPerformed;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "objective_id")
    private Objective objective;

    public Training(TrainingDto trainingDto) {
        this.title = trainingDto.title();
        this.description = trainingDto.description();
        this.date = trainingDto.date();
        this.weekDay = trainingDto.weekDay();
    }

    public Training() {

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

    public Integer getWeekNumber() {
        return weekNumber;
    }

    public void setWeekNumber(Integer weekNumber) {
        this.weekNumber = weekNumber;
    }

    public String getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Objective getObjective() {
        return objective;
    }

    public void setObjective(Objective objective) {
        this.objective = objective;
    }

    public TrainingPerformed getTrainingPerformed() {
        return trainingPerformed;
    }

    public void setTrainingPerformed(TrainingPerformed trainingPerformed) {
        this.trainingPerformed = trainingPerformed;
    }

    public StatusActivity getStatusActivity() {
        return statusActivity;
    }

    public void setStatusActivity(StatusActivity statusActivity) {
        this.statusActivity = statusActivity;
    }
}
