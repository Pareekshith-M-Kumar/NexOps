package com.nexops.analytics.controller;

import com.nexops.analytics.dto.AIAnalyticsSummary;
import com.nexops.analytics.service.AIAnalyticsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai/analytics")
public class AIAnalyticsController {

    private final AIAnalyticsService analyticsService;

    public AIAnalyticsController(AIAnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @GetMapping("/summary")
    public AIAnalyticsSummary getSummary() {
        return analyticsService.getSummary();
    }
}