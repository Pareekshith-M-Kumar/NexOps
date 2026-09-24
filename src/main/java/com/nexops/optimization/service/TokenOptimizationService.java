package com.nexops.optimization.service;

import org.springframework.stereotype.Service;

@Service
public class TokenOptimizationService {

    public String analyzePrompt(String prompt) {

        if (prompt == null || prompt.isBlank()) {
            return "LOW";
        }

        int estimatedTokens =
                (int) Math.ceil(prompt.length() / 4.0);

        if (estimatedTokens < 500) {
            return "LOW";
        }

        if (estimatedTokens < 1500) {
            return "MEDIUM";
        }

        if (estimatedTokens < 3000) {
            return "HIGH";
        }

        return "VERY_HIGH";
    }
}