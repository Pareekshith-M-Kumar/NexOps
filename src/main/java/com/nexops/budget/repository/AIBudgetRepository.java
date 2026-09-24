package com.nexops.budget.repository;

import com.nexops.budget.entity.AIBudget;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AIBudgetRepository
        extends JpaRepository<AIBudget, Long> {

    Optional<AIBudget> findByApplicationId(Long applicationId);
}