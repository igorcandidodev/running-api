package com.runningapi.runningapi.model.strava;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.runningapi.runningapi.model.TrainingPerformed;
import jakarta.persistence.*;

@Entity
@Table(name = "MAP_STRAVA")
public class MapStrava {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String idStrava;

    private String summaryPolyline;

    private Integer resourceState;

    private String polyline;

    @OneToOne(mappedBy = "mapStrava")
    private TrainingPerformed trainingPerformed;

    public MapStrava() {
    }

    public Long getId() {
        return id;
    }

    public String getIdStrava() {
        return idStrava;
    }

    public void setIdStrava(String idStrava) {
        this.idStrava = idStrava;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSummaryPolyline() {
        return summaryPolyline;
    }

    public void setSummaryPolyline(String summaryPolyline) {
        this.summaryPolyline = summaryPolyline;
    }

    public Integer getResourceState() {
        return resourceState;
    }

    public void setResourceState(Integer resourceState) {
        this.resourceState = resourceState;
    }

    public String getPolyline() {
        return polyline;
    }

    public void setPolyline(String polyline) {
        this.polyline = polyline;
    }

    public TrainingPerformed getTrainingPerformed() {
        return trainingPerformed;
    }

    public void setTrainingPerformed(TrainingPerformed trainingPerformed) {
        this.trainingPerformed = trainingPerformed;
    }
}
