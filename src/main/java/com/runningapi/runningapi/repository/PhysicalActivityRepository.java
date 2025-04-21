package com.runningapi.runningapi.repository;

import com.runningapi.runningapi.model.PhysicalActivity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PhysicalActivityRepository extends JpaRepository<PhysicalActivity, Long> {
    Optional<PhysicalActivity> findByUserId(Long userId);
}
