package com.nexops.gateway.service;

import org.springframework.beans.factory.annotation.Value;
import com.nexops.gateway.dto.AIRequest;
import com.nexops.gateway.dto.AIResponse;
import com.nexops.gateway.provider.GrokProvider;
import org.springframework.stereotype.Service;
import com.nexops.gateway.entity.AIRequestLog;
import com.nexops.gateway.repository.AIRequestLogRepository;
import com.nexops.optimization.service.TokenOptimizationService;
import com.nexops.rate.service.AIRateLimitService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import com.nexops.cost.service.CostCalculationService;
import com.nexops.budget.service.AIBudgetService;

import java.util.UUID;

@Service
public class AIGatewayService {

        private final GrokProvider grokProvider;
        private final AIRequestLogRepository aiRequestLogRepository;
        private final CostCalculationService costCalculationService;
        private final AIBudgetService budgetService;
        private final AIRateLimitService rateLimitService;
        private final TokenOptimizationService tokenOptimizationService;

        @Value("${nexops.ai.default-estimated-cost}")
        private BigDecimal defaultEstimatedCost;

        @Value("${nexops.ai.default-max-output-tokens}")
        private Integer defaultMaxOutputTokens;

        @Value("${nexops.ai.max-output-tokens}")
        private Integer maxOutputTokensLimit;

        @Value("${nexops.ai.max-input-tokens}")
        private Integer maxInputTokensLimit;

        public AIGatewayService(
                        GrokProvider grokProvider,
                        AIRequestLogRepository aiRequestLogRepository,
                        CostCalculationService costCalculationService,
                        AIBudgetService budgetService,
                        AIRateLimitService rateLimitService,
                        TokenOptimizationService tokenOptimizationService) {

                this.grokProvider = grokProvider;
                this.aiRequestLogRepository = aiRequestLogRepository;
                this.costCalculationService = costCalculationService;
                this.budgetService = budgetService;
                this.rateLimitService = rateLimitService;
                this.tokenOptimizationService = tokenOptimizationService;
        }

        public AIResponse processRequest(AIRequest request) {

                long startTime = System.currentTimeMillis();

                String requestId = UUID.randomUUID().toString();

                BigDecimal estimatedCost = defaultEstimatedCost;

                if (!budgetService.hasBudgetAvailable(
                                request.getApplicationId(),
                                estimatedCost)) {

                        throw new IllegalArgumentException(
                                        "AI budget exceeded for application "
                                                        + request.getApplicationId());
                }

                Integer maxOutputTokens = request.getMaxOutputTokens();

                if (maxOutputTokens == null) {
                        maxOutputTokens = defaultMaxOutputTokens;
                }

                if (maxOutputTokens > maxOutputTokensLimit) {
                        throw new IllegalArgumentException(
                                        "Maximum allowed output tokens is "
                                                        + maxOutputTokensLimit);
                }
                int estimatedInputTokens = estimateInputTokens(request.getPrompt());

                String optimizationLevel = tokenOptimizationService.analyzePrompt(
                                request.getPrompt());

                if (estimatedInputTokens > maxInputTokensLimit) {

                        throw new IllegalArgumentException(
                                        "Maximum allowed input tokens is "
                                                        + maxInputTokensLimit);
                }

                if (!rateLimitService.isAllowed(
                                request.getApplicationId())) {

                        throw new IllegalArgumentException(
                                        "Rate limit exceeded for application "
                                                        + request.getApplicationId());
                }

                Map<String, Object> groqResponse = grokProvider.generateResponse(
                                request.getModel(),
                                request.getPrompt(),
                                maxOutputTokens);

                Map<String, Object> usage = (Map<String, Object>) groqResponse.get("usage");

                Integer inputTokens = (Integer) usage.get("prompt_tokens");

                Integer outputTokens = (Integer) usage.get("completion_tokens");

                Integer totalTokens = (Integer) usage.get("total_tokens");

                BigDecimal cost = costCalculationService.calculateCost(
                                request.getProvider(),
                                request.getModel(),
                                inputTokens,
                                outputTokens);

                budgetService.addSpending(
                                request.getApplicationId(),
                                cost);

                List<Map<String, Object>> choices = (List<Map<String, Object>>) groqResponse.get("choices");

                Map<String, Object> firstChoice = choices.get(0);

                Map<String, Object> message = (Map<String, Object>) firstChoice.get("message");

                String response = (String) message.get("content");

                long latency = System.currentTimeMillis() - startTime;

                AIRequestLog log = new AIRequestLog(
                                requestId,
                                request.getApplicationId(),
                                request.getProvider(),
                                request.getModel(),
                                inputTokens,
                                outputTokens,
                                totalTokens,
                                latency,
                                "SUCCESS",
                                cost);

                aiRequestLogRepository.save(log);
                return new AIResponse(
                                requestId,
                                request.getApplicationId(),
                                request.getProvider(),
                                request.getModel(),
                                response,
                                inputTokens,
                                outputTokens,
                                totalTokens,
                                latency,
                                "SUCCESS",
                                cost,
                                maxOutputTokens);
        }

        private int estimateInputTokens(String prompt) {

                if (prompt == null || prompt.isBlank()) {
                        return 0;
                }

                return (int) Math.ceil(prompt.length() / 4.0);
        }
}