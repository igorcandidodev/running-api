package com.runningapi.runningapi.repository;

import com.runningapi.runningapi.model.PhysicalLimitation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PhysicalLimitationRepository extends JpaRepository<PhysicalLimitation, Long> {
    Optional<PhysicalLimitation> findByUserId(Long userId);
}
