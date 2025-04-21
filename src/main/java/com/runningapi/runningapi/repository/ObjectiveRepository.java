package com.runningapi.runningapi.repository;

import com.runningapi.runningapi.model.Objective;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ObjectiveRepository extends JpaRepository<Objective, Long> {

    @Query("SELECT o FROM OBJECTIVES o WHERE o.user.id = :userId ORDER BY o.createdAt DESC")
    Optional<Objective> findLatestByUserId(@Param("userId") Long userId);
}