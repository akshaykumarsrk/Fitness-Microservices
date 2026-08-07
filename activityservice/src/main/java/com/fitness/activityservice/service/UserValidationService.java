package com.fitness.activityservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.Duration;

@Service
@RequiredArgsConstructor
@Slf4j   // to see the flow of request by using logger object

// To consume UserService APIs and give data
public class UserValidationService {

    private final WebClient userServiceWebClient;

    public Boolean validateUser(String userId)
    {
        log.info("Calling User Service for {}", userId);
        try
        {
            return userServiceWebClient.get()
                    .uri("/api/v1/users/{userId}/validate", userId)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block(Duration.ofSeconds(15));   // to block thread URI
        } catch (WebClientResponseException e)
        {
            e.printStackTrace();
        }
        return false;
    }

}
