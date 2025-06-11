package com.runningapi.runningapi.repository;

import com.runningapi.runningapi.model.strava.StravaAuthentication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface StravaAuthenticationRepository extends JpaRepository<StravaAuthentication, Long> {
    StravaAuthentication findByUserId(Long userId);
    StravaAuthentication findByUserEmail(String email);
}
