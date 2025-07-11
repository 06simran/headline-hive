package com.headlinehive.model;

public class News {
    private String title;
    private String description;

    public News() {}

    public News(String title, String description) {
        this.title = title;
        this.description = description;
    }

    // Getters & Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
