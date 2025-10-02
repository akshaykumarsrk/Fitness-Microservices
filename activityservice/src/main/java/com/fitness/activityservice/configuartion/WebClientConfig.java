package com.fitness.activityservice.configuartion;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    // to inter communicate with user service we need Web Client Configuration
    @Bean
    @LoadBalanced  // it is important because we are inter service communicating with the help of service name
                   // activity service will not call user service by host name but by service name
                   // because if the port change but the service name would be same
                   // so loadbalance should be necessary to use

    // instance of a web client builder
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    // we are exposing instance of web client in the whole application
    @Bean
    public WebClient userServiceWebClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder.baseUrl("http://USER-SERVICE").build();
    }
}
