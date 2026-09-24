package com.nexops.gateway.dto;

import java.math.BigDecimal;

public class AIResponse {

    private String requestId;
    private Long applicationId;
    private String provider;
    private String model;
    private String response;
    private Integer inputTokens;
    private Integer outputTokens;
    private Integer totalTokens;
    private Long latencyMs;
    private String status;
    private BigDecimal cost;
    private Integer maxOutputTokens;

    public AIResponse(
            String requestId,
            Long applicationId,
            String provider,
            String model,
            String response,
            Integer inputTokens,
            Integer outputTokens,
            Integer totalTokens,
            Long latencyMs,
            String status,
            BigDecimal cost,
            Integer maxOutputTokens) {

        this.requestId = requestId;
        this.applicationId = applicationId;
        this.provider = provider;
        this.model = model;
        this.response = response;
        this.inputTokens = inputTokens;
        this.outputTokens = outputTokens;
        this.totalTokens = totalTokens;
        this.latencyMs = latencyMs;
        this.status = status;
        this.cost = cost;
        this.maxOutputTokens = maxOutputTokens;
    }

    public String getRequestId() {
        return requestId;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public String getProvider() {
        return provider;
    }

    public String getModel() {
        return model;
    }

    public String getResponse() {
        return response;
    }

    public Integer getInputTokens() {
        return inputTokens;
    }

    public Integer getOutputTokens() {
        return outputTokens;
    }

    public Integer getTotalTokens() {
        return totalTokens;
    }

    public Long getLatencyMs() {
        return latencyMs;
    }

    public String getStatus() {
        return status;
    }

    public BigDecimal getCost() {
        return cost;
    }
    public Integer getMaxOutputTokens() {
    return maxOutputTokens;
}
}