package com.runningapi.runningapi.repository;

import com.runningapi.runningapi.model.Training;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TrainingRepository extends JpaRepository<Training, Long> {

    @Query("SELECT t FROM TRAININGS t " +
            "          JOIN t.objective " +
            "WHERE (FUNCTION('TO_CHAR', t.date, 'DD/MM/YYYY') = ?1 " +
            "OR t.date = FUNCTION('DATE_SUB', CURRENT_DATE, 1))" +
            "AND t.objective.user.id = ?2 " +
            "AND t.objective.status = 'ACTIVE'" +
            "ORDER BY t.date DESC")
    List<Training> findByDateAndUserId(String date, Long userId, Pageable pageable);

    @Query("SELECT t FROM TRAININGS t " +
            "           JOIN t.trainingPerformed " +
            "WHERE t.trainingPerformed.idStrava = ?1")
    Optional<Training> findByIdStrava(Long idStrava);

    @Query("SELECT t FROM TRAININGS t " +
            "          JOIN t.objective o " +
            "WHERE o.user.email = ?1")
    List<Training> findAllTrainingByUserEmail(String email);

    @Query("SELECT t FROM TRAININGS t " +
            "          JOIN t.objective o " +
            "WHERE o.user.email = ?1 " +
            "AND CAST(t.date AS date) >= CAST(?2 AS date) " +
            "AND CAST(t.date AS date) <= CAST(?3 AS date) ")
    List<Training> findAllForPeriodByUserEmail(String email, LocalDate startDate, LocalDate endDate);
}
