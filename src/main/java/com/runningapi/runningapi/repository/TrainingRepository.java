package com.runningapi.runningapi.repository;

import com.runningapi.runningapi.model.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrainingRepository extends JpaRepository<Training, Long> {

    @Query("SELECT t FROM TRAININGS t " +
            "          JOIN t.objective " +
            "WHERE (FUNCTION('TO_CHAR', t.date, 'DD/MM/YYYY') = ?1 " +
            "OR t.date = FUNCTION('DATE_SUB', CURRENT_DATE, 1))" +
            "AND t.objective.user.id = ?2 " +
            "ORDER BY t.date DESC")
    Optional<Training> findByDateAndUserId(String date, Long userId);

    @Query("SELECT t FROM TRAININGS t " +
            "           JOIN t.trainingPerformed " +
            "WHERE t.trainingPerformed.idStrava = ?1")
    Optional<Training> findByIdStrava(Long idStrava);
}
