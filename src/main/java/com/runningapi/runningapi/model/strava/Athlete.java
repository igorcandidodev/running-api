package com.runningapi.runningapi.model.strava;

import jakarta.persistence.*;

import java.time.ZonedDateTime;

@Entity
@Table(name = "strava_athlete")
public class Athlete {

    @Id
    private Long id;

    private String username;

    private String resourceState;

    private String firstname;

    private String lastname;

    private String bio;

    private String city;

    private String state;

    private String country;

    private String sex;

    private String premium;

    private String summit;

    private ZonedDateTime createdAt;

    private ZonedDateTime updatedAt;

    private Integer badgeTypeId;

    private String weight;

    private String profileMedium;

    private String profile;

    private String friend;

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

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getBadgeTypeId() {
        return badgeTypeId;
    }

    public void setBadgeTypeId(Integer badgeTypeId) {
        this.badgeTypeId = badgeTypeId;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getProfileMedium() {
        return profileMedium;
    }

    public void setProfileMedium(String profileMedium) {
        this.profileMedium = profileMedium;
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
