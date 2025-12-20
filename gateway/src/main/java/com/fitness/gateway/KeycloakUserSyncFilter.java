package com.fitness.gateway;

import com.fitness.gateway.user.UserRequest;
import com.fitness.gateway.user.UserService;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@RequiredArgsConstructor

// WebFilter is a filter though which we can intercept the request
public class KeycloakUserSyncFilter implements WebFilter
{

    private final UserService userService;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String userId = exchange.getRequest().getHeaders().getFirst("X-User-ID");
        String token = exchange.getRequest().getHeaders().getFirst("Authorization");
<<<<<<< HEAD
        UserRequest registerRequest = getUserDetails(token);
=======
        UserRequest registerRequest = getUserDetals(token);
>>>>>>> 65e5e6f04cb6da8729f16b8c153f642faadfb8c3

        if(userId == null)
        {
            userId = registerRequest.getKeycloakId();
        }

        if(userId != null && token != null)
        {
            // flatMap is not in suggestioon so, we have to write Mono<Boolean>
            // instead of Boolean return type in validateUser
            String finalUserId = userId;
            return userService.validateUser(userId)
                    .flatMap(exist -> {
                        if(!exist)
                        {
                            if(registerRequest != null)
                            {
                                return userService.registerUser(registerRequest)
                                        .then(Mono.empty());
                            }
                            else
                            {
                                return Mono.empty();
                            }
                        }
                        else
                        {
                            log.info("User already exist, Skipping sync");
                            return Mono.empty();
                        }
                    })
                    // defer means that don't run the lower part of code until the upper part of the code runs completely
                    .then(Mono.defer(() -> {
                        ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                                .header("X-User-ID", finalUserId)
                                .build();
                        return chain.filter(exchange.mutate().request(mutatedRequest).build());
                    }));

        }
        return chain.filter(exchange);
    }

<<<<<<< HEAD
    private UserRequest getUserDetails(String token)
=======
    private UserRequest getUserDetals(String token)
>>>>>>> 65e5e6f04cb6da8729f16b8c153f642faadfb8c3
    {
        try
        {
            // Token is passed in Bearer<TOKEN> format by default
            String tokenWithoutBearer = token.replace("Bearer", "").trim();
            SignedJWT signedJWT = SignedJWT.parse(tokenWithoutBearer);
            JWTClaimsSet claims = signedJWT.getJWTClaimsSet();

            UserRequest userRequest = new UserRequest();
            userRequest.setEmail(claims.getStringClaim("email"));
            userRequest.setKeycloakId(claims.getStringClaim("sub"));
            userRequest.setFirstName(claims.getStringClaim("given_name"));
            userRequest.setLastName(claims.getStringClaim("family_name"));
            userRequest.setPassword("dummy@123123");

            return userRequest;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
