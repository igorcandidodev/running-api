package com.runningapi.runningapi.repository;

import com.runningapi.runningapi.model.Objective;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ObjectiveRepository extends JpaRepository<Objective, Long> {

    @Query(value = "SELECT * FROM OBJECTIVES o WHERE o.user_id = :userId ORDER BY o.createdat DESC LIMIT 1", nativeQuery = true)
    Optional<Objective> findLatestByUserId(@Param("userId") Long userId);

    List<Objective> findAllByUserEmail(String email);
}