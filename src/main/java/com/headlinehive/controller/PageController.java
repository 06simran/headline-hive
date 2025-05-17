package com.headlinehive.controller;

import com.headlinehive.model.Article;
import com.headlinehive.model.User;
import com.headlinehive.service.NewsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PageController {

    private static final Logger logger = LoggerFactory.getLogger(PageController.class);

    @Autowired
    private NewsService newsService;

    @GetMapping("/")
    public String index(@RequestParam(required = false) String category, Model model) {
        logger.info("Loading home page with category: {}", category);
        List<Article> articles = newsService.fetchNews(category);
        logger.info("Articles fetched: {}", articles.size());

        model.addAttribute("articles", articles);
        model.addAttribute("user", new User());  // only if your index.html has a form bound to user
        return "index";
    }
}
