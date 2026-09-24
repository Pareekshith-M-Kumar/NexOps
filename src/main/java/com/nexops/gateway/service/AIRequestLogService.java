package com.nexops.gateway.service;

import com.nexops.gateway.entity.AIRequestLog;
import com.nexops.gateway.repository.AIRequestLogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AIRequestLogService {

    private final AIRequestLogRepository repository;

    public AIRequestLogService(AIRequestLogRepository repository) {
        this.repository = repository;
    }

    public List<AIRequestLog> getAllLogs() {
        return repository.findAll();
    }
}