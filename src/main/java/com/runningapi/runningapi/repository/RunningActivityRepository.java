package com.runningapi.runningapi.repository;

import com.runningapi.runningapi.model.RunningActivity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RunningActivityRepository extends JpaRepository<RunningActivity, Long> {
    List<RunningActivity> findTop2ByUserIdOrderByDateDesc(Long userId);

    List<RunningActivity> findTop2ByUserIdAndIsBestResultTrueOrderByDateDesc(Long userId);

    Optional<RunningActivity> findByUserId(Long userId);
}