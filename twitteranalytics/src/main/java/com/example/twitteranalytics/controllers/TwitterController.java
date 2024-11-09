package com.example.twitteranalytics.controllers;

import com.example.twitteranalytics.models.Tweet;
import com.example.twitteranalytics.models.User;
import com.example.twitteranalytics.services.TwitterService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
public class TwitterController {

    private final TwitterService twitterService;

    public TwitterController(TwitterService twitterService) {
        this.twitterService = twitterService;
    }

    @GetMapping("/api/twitter/user/{userId}/tweets")
    public Mono<List<Tweet>> getUserTweets(@PathVariable String userId) {
        return twitterService.getUserTweets(userId);
    }

    @GetMapping("/api/twitter/user/by-username/{username}")
    public Mono<User> getUserByUsername(@PathVariable String username) {
        return twitterService.getUserByUsername(username);
    }
}
