package com.runningapi.runningapi.repository;

import com.runningapi.runningapi.model.strava.MapStrava;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MapStravaRepository extends JpaRepository<MapStrava, Long> {
}
