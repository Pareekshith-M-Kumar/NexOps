package com.nexops.budget.controller;

import com.nexops.budget.dto.AIBudgetResponse;
import com.nexops.budget.entity.AIBudget;
import com.nexops.budget.service.AIBudgetService;
import org.springframework.web.bind.annotation.*;
import com.nexops.budget.dto.AIBudgetResponse;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/budgets")
public class AIBudgetController {

    private final AIBudgetService budgetService;

    public AIBudgetController(AIBudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @PostMapping
    public AIBudget createBudget(
            @RequestParam Long applicationId,
            @RequestParam BigDecimal budgetAmount) {

        return budgetService.createBudget(
                applicationId,
                budgetAmount);
    }

    @GetMapping("/{applicationId}")
    public AIBudgetResponse getBudget(@PathVariable Long applicationId) {
        return budgetService.getBudgetResponse(applicationId);
    }

    @GetMapping("/{applicationId}/status")
    public String getBudgetStatus(@PathVariable Long applicationId) {

        return budgetService.getBudgetStatus(applicationId);
    }
}