package com.runningapi.runningapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;

@Entity(name = "PHYSICAL_LIMITATIONS")
public class PhysicalLimitation implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private boolean feltPain;
    private String description;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;

    public PhysicalLimitation(boolean feltPain, String description, User user) {
        this.feltPain = feltPain;
        this.description = description;
        this.user = user;
    }

    public PhysicalLimitation() {

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isFeltPain() {
        return feltPain;
    }

    public void setFeltPain(boolean feltPain) {
        this.feltPain = feltPain;
    }
}
