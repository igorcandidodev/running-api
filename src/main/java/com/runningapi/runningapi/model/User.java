package com.runningapi.runningapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.runningapi.runningapi.model.strava.StravaAuthentication;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity(name = "USERS")
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String fullName;
    @Column(nullable = false)
    private LocalDate birthDate;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<RunningActivity> runningHistory;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<PhysicalActivity> physicalActivityHistory;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<PhysicalLimitation> physicalLimitation;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Objective> objectives;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private StravaAuthentication stravaAuthentication;

    public User() {
    }

    public User(Long id, String fullName, LocalDate birthDate, String email, String password, List<Objective> objectives, List<RunningActivity> runningHistory, List<PhysicalActivity> physicalActivityHistory, List<PhysicalLimitation> physicalLimitation) {
        this.id = id;
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.email = email;
        this.password = password;
        this.objectives = objectives;
        this.runningHistory = runningHistory;
        this.physicalActivityHistory = physicalActivityHistory;
        this.physicalLimitation = physicalLimitation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Objective> getObjectives() {
        return objectives;
    }

    public void setObjectives(List<Objective> objectives) {
        this.objectives = objectives;
    }

    public List<RunningActivity> getRunningHistory() {
        return runningHistory;
    }

    public void setRunningHistory(List<RunningActivity> runningHistory) {
        this.runningHistory = runningHistory;
    }

    public List<PhysicalActivity> getPhysicalActivityHistory() {
        return physicalActivityHistory;
    }

    public void setPhysicalActivityHistory(List<PhysicalActivity> physicalActivityHistory) {
        this.physicalActivityHistory = physicalActivityHistory;
    }

    public List<PhysicalLimitation> getPhysicalLimitation() {
        return physicalLimitation;
    }

    public void setPhysicalLimitation(List<PhysicalLimitation> physicalLimitation) {
        this.physicalLimitation = physicalLimitation;
    }

    public StravaAuthentication getStravaAuthentication() {
        return stravaAuthentication;
    }

    public void setStravaAuthentication(StravaAuthentication stravaAuthentication) {
        this.stravaAuthentication = stravaAuthentication;
    }
}
