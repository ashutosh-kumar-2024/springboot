package com.example.twitteranalytics.models;

import lombok.Data;

@Data
public class Tweet {
    private String id;
    private String text;
    private String authorId;
    private String createdAt;
}