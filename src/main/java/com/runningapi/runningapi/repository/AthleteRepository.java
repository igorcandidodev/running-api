package com.runningapi.runningapi.repository;

import com.runningapi.runningapi.model.strava.Athlete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AthleteRepository extends JpaRepository<Athlete, Long> {
}
