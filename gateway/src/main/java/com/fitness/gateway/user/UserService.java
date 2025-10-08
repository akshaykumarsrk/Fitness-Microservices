package com.fitness.gateway.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j   // to see the flow of request by using logger object

// To consume UserService APIs and give data
public class UserService {

    private final WebClient userServiceWebClient;

    // we have used Mono because it allows us to deal things in asynchronous way
    // it is an reactive type i.e it comes from reacter project
    // it represents single value just like Promise
    // Mono is just like a promise
    // that i am promising that in future there will a value
    // why future because this is an API call
    public Mono<Boolean> validateUser(String userId)
    {
        log.info("Calling User Service for {}", userId);
        return userServiceWebClient.get()
                .uri("/api/v1/users/{userId}/validate", userId)
                .retrieve()
                .bodyToMono(Boolean.class)
                .onErrorResume(WebClientResponseException.class, e -> {
                    if (e.getStatusCode() == HttpStatus.NOT_FOUND)
                        return Mono.error(new RuntimeException("User not found: " + userId));

                    else if (e.getStatusCode() == HttpStatus.BAD_REQUEST)
                        return Mono.error(new RuntimeException("Invalid: " + userId));

                    return Mono.error(new RuntimeException("Unexpected error: " + userId));
                });
    }

    public Mono<UserResponse> registerUser(UserRequest registerRequest)
    {
        log.info("Calling User Registration for {}", registerRequest.getEmail());
        return userServiceWebClient.post()
                .uri("/api/v1/users/register")
                .bodyValue(registerRequest)
                .retrieve()
                .bodyToMono(UserResponse.class)
                .onErrorResume(WebClientResponseException.class, e -> {
                    if (e.getStatusCode() == HttpStatus.BAD_REQUEST)
                        return Mono.error(new RuntimeException("Bad request: " + e.getMessage()));

                    return Mono.error(new RuntimeException("Unexpected error: " + e.getMessage()));
                });
    }
}
