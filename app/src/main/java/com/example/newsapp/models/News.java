package com.example.newsapp.models;

public class News {
    private String title;
    private String description;
    private String imageUrl;

    public News(String title, String desc, String imageUrl) {
        this.title = title;
        this.description = desc;
        this.imageUrl = imageUrl;
    }

    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getImageUrl() { return imageUrl; }
}
