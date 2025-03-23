package com.runningapi.runningapi.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;

@Entity(name = "TRAININGS_PERFORMEDS")
public class TrainingPerformed implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    private String description;
    @Column(nullable = false)
    private LocalDate date;
    @Column(nullable = false)
    private DayOfWeek dayOfWeekName;
    @OneToOne
    @JoinColumn(name = "training_id")
    private Training training;
    @ManyToOne
    @JoinColumn(name = "objective_id", nullable = false)
    private Objective objective;

    public TrainingPerformed(Long id, String title, String description, LocalDate date, DayOfWeek dayOfWeekName, Training training, Objective objective) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
        this.dayOfWeekName = dayOfWeekName;
        this.training = training;
        this.objective = objective;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public DayOfWeek getDayOfWeekName() {
        return dayOfWeekName;
    }

    public void setDayOfWeekName(DayOfWeek dayOfWeekName) {
        this.dayOfWeekName = dayOfWeekName;
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
}
