package com.runningapi.runningapi.repository;

import com.runningapi.runningapi.model.TrainingPerformed;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TrainingPerformedRepository extends JpaRepository<TrainingPerformed, Long> {

   Optional<TrainingPerformed> findByIdStrava(Long idStrava);

}
