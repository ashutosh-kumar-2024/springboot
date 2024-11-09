package com.example.twitteranalytics.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class TwitterConfig {

    @Value("${twitter.api.base-url}")
    private String baseUrl;

    @Value("${twitter.api.bearer-token}")
    private String bearerToken;

    @Bean
    public WebClient twitterWebClient() {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("Authorization", "Bearer " + bearerToken)
                .build();
    }
}
