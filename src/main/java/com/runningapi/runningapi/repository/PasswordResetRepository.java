package com.runningapi.runningapi.repository;

import com.runningapi.runningapi.model.PasswordReset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetRepository extends JpaRepository<PasswordReset, Long> {
    Optional<PasswordReset> findByCode(String code);

    Optional<PasswordReset> findByUserEmail(String email);

}