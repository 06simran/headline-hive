package com.headlinehive.controller;

import com.headlinehive.model.Article;
import com.headlinehive.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @GetMapping
    public List<Article> getNews(@RequestParam(required = false) String category) {
        // If no category or 'all' is selected, fetch all news
        if (category == null || category.equalsIgnoreCase("all")) {
            return newsService.fetchNews(null);  // Pass null to fetch all categories
        }
        return newsService.fetchNews(category);  // Fetch specific category
    }
}
