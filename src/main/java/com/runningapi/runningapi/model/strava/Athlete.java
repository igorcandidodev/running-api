package com.runningapi.runningapi.model.strava;


import jakarta.persistence.*;

import java.time.ZonedDateTime;

@Entity
@Table(name = "strava_athlete")
public class Athlete {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "resource_state")
    private String resourceState;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "bio")
    private String bio;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "country")
    private String country;

    @Column(name = "sex")
    private String sex;

    @Column(name = "premium")
    private String premium;

    @Column(name = "summit")
    private String summit;

    @Column(name = "created_at")
    private ZonedDateTime created_at;

    @Column(name = "updated_at")
    private ZonedDateTime updated_at;

    @Column(name = "badge_type_id")
    private Integer badge_type_id;

    @Column(name = "weight")
    private String weight;

    @Column(name = "profile_medium")
    private String profile_medium;

    @Column(name = "profile")
    private String profile;

    @Column(name = "friend")
    private String friend;

    @Column(name = "follower")
    private String follower;

    @OneToOne(mappedBy = "athlete", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private StravaAuthentication authentication;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getResourceState() {
        return resourceState;
    }

    public void setResourceState(String resourceState) {
        this.resourceState = resourceState;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPremium() {
        return premium;
    }

    public void setPremium(String premium) {
        this.premium = premium;
    }

    public String getSummit() {
        return summit;
    }

    public void setSummit(String summit) {
        this.summit = summit;
    }

    public ZonedDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(ZonedDateTime created_at) {
        this.created_at = created_at;
    }

    public ZonedDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(ZonedDateTime updated_at) {
        this.updated_at = updated_at;
    }

    public Integer getBadge_type_id() {
        return badge_type_id;
    }

    public void setBadge_type_id(Integer badge_type_id) {
        this.badge_type_id = badge_type_id;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getProfile_medium() {
        return profile_medium;
    }

    public void setProfile_medium(String profile_medium) {
        this.profile_medium = profile_medium;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getFriend() {
        return friend;
    }

    public void setFriend(String friend) {
        this.friend = friend;
    }

    public String getFollower() {
        return follower;
    }

    public void setFollower(String follower) {
        this.follower = follower;
    }

    public StravaAuthentication getAuthentication() {
        return authentication;
    }

    public void setAuthentication(StravaAuthentication authentication) {
        this.authentication = authentication;
    }
}
