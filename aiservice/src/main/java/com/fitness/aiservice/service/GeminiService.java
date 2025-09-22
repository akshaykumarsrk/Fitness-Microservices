package com.fitness.aiservice.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.Map;

@Service
public class GeminiService
{
    // by the help of this web client instance, we will call Gemini API and give it to Activity data and present
    // and then generated Response comes, we will parse it and store in our database
    private final WebClient webClient;

    @Value("${gemini.api.url}")
    private String geminiApiUrl;

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    public GeminiService(WebClient.Builder webClientBuilder)
    {
        this.webClient = webClientBuilder.build();
    }

    // Gemini APIs give response in this format
    public String getRecommendations(String details)
    {
        Map<String, Object> requestBody = Map.of(
                "contents", new Object[] {
                        Map.of("parts", new Object[] {
                                Map.of("text", details)
                        })
                }
        );

        // call the API
        String response = webClient.post()
                .uri(geminiApiUrl)
                .header("Content_Type", "application/json")
                .header("X-goog-api-key", geminiApiKey)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return response;
    }
}
