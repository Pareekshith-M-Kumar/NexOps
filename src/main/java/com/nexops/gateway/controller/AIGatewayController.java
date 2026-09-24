package com.nexops.gateway.controller;

import com.nexops.gateway.dto.AIRequest;
import com.nexops.gateway.dto.AIResponse;
import com.nexops.gateway.service.AIGatewayService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
public class AIGatewayController {

    private final AIGatewayService gatewayService;

    public AIGatewayController(
            AIGatewayService gatewayService) {

        this.gatewayService = gatewayService;
    }

    @PostMapping("/chat")
    public AIResponse chat(
            @Valid @RequestBody AIRequest request) {

        return gatewayService.processRequest(request);
    }
}