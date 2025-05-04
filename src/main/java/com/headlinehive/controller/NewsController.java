package com.headlinehive.controller;

import com.headlinehive.service.NewsService;
import com.headlinehive.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
public class NewsController {

    private final NewsService newsService;

    // Constructor-based injection
    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/")
    public String getTopArticles(Model model) {
        // Fetch top articles using NewsService
        List<Article> articles = newsService.getTopArticles();

        // Add articles to the model
        model.addAttribute("articles", articles);
        return "index";  // Thymeleaf view name (index.html)
    }

}
