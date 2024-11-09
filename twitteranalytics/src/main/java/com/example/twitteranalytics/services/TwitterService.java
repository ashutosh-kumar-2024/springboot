package com.example.twitteranalytics.services;

import com.example.twitteranalytics.models.Tweet;
import com.example.twitteranalytics.models.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class TwitterService {

    private final WebClient twitterWebClient;

    @Value("${twitter.api.bearer-token}")
    private String bearerToken;

    public TwitterService(WebClient twitterWebClient) {
        this.twitterWebClient = twitterWebClient;
    }

    @Cacheable("userTweets")
    public Mono<List<Tweet>> getUserTweets(String userId) {
        return twitterWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/users/{userId}/tweets")
                        .queryParam("max_results", 5)
                        .build(userId))
                .header("Authorization", "Bearer " + bearerToken)
                .retrieve()
                .bodyToMono(TweetResponse.class)
                .map(TweetResponse::getData);
    }

    @Cacheable("userByUsername")
    public Mono<User> getUserByUsername(String username) {
        return twitterWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/users/by/username/{username}")
                        .build(username))
                .header("Authorization", "Bearer " + bearerToken)
                .retrieve()
                .bodyToMono(UserResponse.class)
                .map(UserResponse::getData);
    }

    private static class TweetResponse {
        private List<Tweet> data;

        public List<Tweet> getData() {
            return data;
        }

        public void setData(List<Tweet> data) {
            this.data = data;
        }
    }

    private static class UserResponse {
        private User data;

        public User getData() {
            return data;
        }

        public void setData(User data) {
            this.data = data;
        }
    }
}
