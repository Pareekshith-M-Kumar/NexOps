package com.nexops.budget.service;

import com.nexops.budget.dto.AIBudgetResponse;
import com.nexops.budget.entity.AIBudget;
import com.nexops.budget.repository.AIBudgetRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class AIBudgetService {

        private final AIBudgetRepository repository;

        @Value("${nexops.ai.budget-warning-threshold}")
        private BigDecimal budgetWarningThreshold;

        public AIBudgetService(AIBudgetRepository repository) {
                this.repository = repository;
        }

        public AIBudget createBudget(
                        Long applicationId,
                        BigDecimal budgetAmount) {

                if (budgetAmount == null ||
                                budgetAmount.compareTo(BigDecimal.ZERO) <= 0) {

                        throw new IllegalArgumentException(
                                        "Budget amount must be greater than zero");
                }

                if (repository.findByApplicationId(applicationId).isPresent()) {

                        throw new IllegalArgumentException(
                                        "Budget already exists for application "
                                                        + applicationId);
                }

                AIBudget budget = new AIBudget(
                                applicationId,
                                budgetAmount);

                return repository.save(budget);
        }

        public AIBudget getBudget(Long applicationId) {

                return repository
                                .findByApplicationId(applicationId)
                                .orElseThrow(() -> new RuntimeException(
                                                "Budget not found for application "
                                                                + applicationId));
        }

        // NEW METHOD
        public AIBudgetResponse getBudgetResponse(Long applicationId) {

                AIBudget budget = repository
                                .findByApplicationId(applicationId)
                                .orElseThrow(() -> new RuntimeException(
                                                "Budget not found for application "
                                                                + applicationId));

                // Calculate remaining budget
                BigDecimal remainingAmount = budget.getBudgetAmount()
                                .subtract(budget.getUsedAmount());

                // Calculate utilization percentage
                BigDecimal utilizationPercentage = budget.getUsedAmount()
                                .multiply(BigDecimal.valueOf(100))
                                .divide(
                                                budget.getBudgetAmount(),
                                                2,
                                                RoundingMode.HALF_UP);

                // Return DTO
                String budgetStatus = getBudgetStatus(applicationId);
                return new AIBudgetResponse(
                                budget.getId(),
                                budget.getApplicationId(),
                                budget.getBudgetAmount(),
                                budget.getUsedAmount(),
                                remainingAmount,
                                utilizationPercentage,
                                budgetStatus,
                                budget.getCreatedAt(),
                                budget.getUpdatedAt());
        }

        public String getBudgetStatus(Long applicationId) {

                AIBudget budget = repository
                                .findByApplicationId(applicationId)
                                .orElseThrow(() -> new RuntimeException(
                                                "Budget not found for application "
                                                                + applicationId));

                BigDecimal utilizationPercentage = budget.getUsedAmount()
                                .multiply(BigDecimal.valueOf(100))
                                .divide(
                                                budget.getBudgetAmount(),
                                                2,
                                                RoundingMode.HALF_UP);

                if (utilizationPercentage.compareTo(BigDecimal.valueOf(100)) >= 0) {
                        return "EXCEEDED";
                }

                if (utilizationPercentage.compareTo(budgetWarningThreshold) >= 0) {
                        return "WARNING";
                }

                return "NORMAL";
        }

        public boolean hasBudgetAvailable(
                        Long applicationId,
                        BigDecimal estimatedCost) {

                AIBudget budget = repository
                                .findByApplicationId(applicationId)
                                .orElseThrow(() -> new RuntimeException(
                                                "Budget not found for application "
                                                                + applicationId));

                BigDecimal remainingAmount = budget.getBudgetAmount()
                                .subtract(budget.getUsedAmount());

                return remainingAmount.compareTo(estimatedCost) >= 0;
        }

        public AIBudget addSpending(
                        Long applicationId,
                        BigDecimal cost) {

                AIBudget budget = repository
                                .findByApplicationId(applicationId)
                                .orElseThrow(() -> new RuntimeException(
                                                "Budget not found for application "
                                                                + applicationId));

                BigDecimal newUsedAmount = budget.getUsedAmount().add(cost);

                budget.setUsedAmount(newUsedAmount);

                return repository.save(budget);
        }
}