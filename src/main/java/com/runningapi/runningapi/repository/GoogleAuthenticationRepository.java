package com.runningapi.runningapi.repository;

import com.runningapi.runningapi.model.google.GoogleAuthentication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoogleAuthenticationRepository extends JpaRepository<GoogleAuthentication, Long> {
    GoogleAuthentication findByUserId(Long userId);
}