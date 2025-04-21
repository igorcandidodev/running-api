package com.runningapi.runningapi.repository;

import com.runningapi.runningapi.model.PromptTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PromptTemplateRepository extends JpaRepository<PromptTemplate, Long> {
    Optional<PromptTemplate> findById(Long id);
}
