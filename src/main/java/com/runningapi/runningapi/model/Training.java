package com.runningapi.runningapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.DayOfWeek;
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
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private String weeklyGoal;
    @Column(nullable = false)
    private LocalDate date;
    @Column(nullable = false)
    private DayOfWeek dayOfWeekName;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "objective_id")
    private Objective objective;

    public Training(Long id, String title, String description, String weeklyGoal, LocalDate date, DayOfWeek dayOfWeekName, Objective objective) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.weeklyGoal = weeklyGoal;
        this.date = date;
        this.dayOfWeekName = dayOfWeekName;
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

    public String getWeeklyGoal() {
        return weeklyGoal;
    }

    public void setWeeklyGoal(String weeklyGoal) {
        this.weeklyGoal = weeklyGoal;
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

    public Objective getObjective() {
        return objective;
    }

    public void setObjective(Objective objective) {
        this.objective = objective;
    }
}
