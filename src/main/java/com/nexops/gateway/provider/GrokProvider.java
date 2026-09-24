package com.nexops.gateway.provider;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Component
public class GrokProvider {

        private final RestClient restClient;

        public GrokProvider() {

                String apiKey = System.getenv("GROQ_API_KEY");

                if (apiKey == null || apiKey.isBlank()) {
                        throw new IllegalStateException("GROQ_API_KEY is not configured");
                }

                this.restClient = RestClient.builder()
                                .baseUrl("https://api.groq.com/openai/v1")
                                .defaultHeader(
                                                HttpHeaders.AUTHORIZATION,
                                                "Bearer " + apiKey)
                                .defaultHeader(
                                                HttpHeaders.CONTENT_TYPE,
                                                MediaType.APPLICATION_JSON_VALUE)
                                .build();
        }

        public Map<String, Object> generateResponse(
                        String model,
                        String prompt,
                        Integer maxOutputTokens) {

                Map<String, Object> message = Map.of(
                                "role", "user",
                                "content", prompt);

                Map<String, Object> requestBody = Map.of(
                                "model", model,
                                "messages", List.of(message),
                                "max_completion_tokens", maxOutputTokens);

                return restClient.post()
                                .uri("/chat/completions")
                                .body(requestBody)
                                .retrieve()
                                .body(Map.class);
        }
}