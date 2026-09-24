package com.nexops.rate.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class AIRateLimitService {

    private final RedisTemplate<String, String> redisTemplate;

    @Value("${nexops.ai.rate-limit.requests}")
    private Integer maxRequests;

    @Value("${nexops.ai.rate-limit.window-seconds}")
    private Integer windowSeconds;

    public AIRateLimitService(
            RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public boolean isAllowed(Long applicationId) {

        String key = "rate-limit:app:" + applicationId;

        Long requestCount =
                redisTemplate.opsForValue().increment(key);

        if (requestCount == 1) {
            redisTemplate.expire(
                    key,
                    windowSeconds,
                    TimeUnit.SECONDS
            );
        }

        return requestCount <= maxRequests;
    }
}