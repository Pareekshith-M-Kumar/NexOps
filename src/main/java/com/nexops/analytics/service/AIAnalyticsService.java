package com.nexops.analytics.service;

import com.nexops.analytics.dto.AIAnalyticsSummary;
import com.nexops.gateway.entity.AIRequestLog;
import com.nexops.gateway.repository.AIRequestLogRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class AIAnalyticsService {

    private final AIRequestLogRepository repository;

    public AIAnalyticsService(AIRequestLogRepository repository) {
        this.repository = repository;
    }

    public AIAnalyticsSummary getSummary() {

        List<AIRequestLog> logs = repository.findAll();

        long totalRequests = logs.size();

        long totalInputTokens = logs.stream()
                .mapToLong(log -> log.getInputTokens())
                .sum();

        long totalOutputTokens = logs.stream()
                .mapToLong(log -> log.getOutputTokens())
                .sum();

        long totalTokens = logs.stream()
                .mapToLong(log -> log.getTotalTokens())
                .sum();

        BigDecimal totalCost = logs.stream()
                .map(AIRequestLog::getCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        double averageLatencyMs = logs.stream()
                .mapToLong(AIRequestLog::getLatencyMs)
                .average()
                .orElse(0.0);

        totalCost = totalCost.setScale(8, RoundingMode.HALF_UP);

        return new AIAnalyticsSummary(
                totalRequests,
                totalInputTokens,
                totalOutputTokens,
                totalTokens,
                totalCost,
                averageLatencyMs
        );
    }
}