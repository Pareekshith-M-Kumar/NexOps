package com.nexops.budget.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AIBudgetResponse {

    private Long id;
    private Long applicationId;
    private BigDecimal budgetAmount;
    private BigDecimal usedAmount;
    private BigDecimal remainingAmount;
    private BigDecimal utilizationPercentage;
    private String budgetStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public AIBudgetResponse(
            Long id,
            Long applicationId,
            BigDecimal budgetAmount,
            BigDecimal usedAmount,
            BigDecimal remainingAmount,
            BigDecimal utilizationPercentage,
            String budgetStatus,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {

        this.id = id;
        this.applicationId = applicationId;
        this.budgetAmount = budgetAmount;
        this.usedAmount = usedAmount;
        this.remainingAmount = remainingAmount;
        this.utilizationPercentage = utilizationPercentage;
        this.budgetStatus = budgetStatus;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public BigDecimal getBudgetAmount() {
        return budgetAmount;
    }

    public BigDecimal getUsedAmount() {
        return usedAmount;
    }

    public BigDecimal getRemainingAmount() {
        return remainingAmount;
    }

    public BigDecimal getUtilizationPercentage() {
        return utilizationPercentage;
    }

    public String getBudgetStatus() {
        return budgetStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}