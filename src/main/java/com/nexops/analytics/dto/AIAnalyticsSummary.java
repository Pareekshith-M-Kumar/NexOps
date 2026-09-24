package com.nexops.analytics.dto;

import java.math.BigDecimal;

public class AIAnalyticsSummary {

    private long totalRequests;

    private long totalInputTokens;

    private long totalOutputTokens;

    private long totalTokens;

    private BigDecimal totalCost;

    private double averageLatencyMs;

    public AIAnalyticsSummary(
            long totalRequests,
            long totalInputTokens,
            long totalOutputTokens,
            long totalTokens,
            BigDecimal totalCost,
            double averageLatencyMs
    ) {
        this.totalRequests = totalRequests;
        this.totalInputTokens = totalInputTokens;
        this.totalOutputTokens = totalOutputTokens;
        this.totalTokens = totalTokens;
        this.totalCost = totalCost;
        this.averageLatencyMs = averageLatencyMs;
    }

    public long getTotalRequests() {
        return totalRequests;
    }

    public long getTotalInputTokens() {
        return totalInputTokens;
    }

    public long getTotalOutputTokens() {
        return totalOutputTokens;
    }

    public long getTotalTokens() {
        return totalTokens;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public double getAverageLatencyMs() {
        return averageLatencyMs;
    }
}