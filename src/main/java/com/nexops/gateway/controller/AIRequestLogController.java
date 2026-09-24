package com.nexops.gateway.controller;

import com.nexops.gateway.entity.AIRequestLog;
import com.nexops.gateway.service.AIRequestLogService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ai/logs")
public class AIRequestLogController {

    private final AIRequestLogService service;

    public AIRequestLogController(AIRequestLogService service) {
        this.service = service;
    }

    @GetMapping
    public List<AIRequestLog> getAllLogs() {
        return service.getAllLogs();
    }
}