package com.headlinehive.service;

import com.headlinehive.model.Article;
import com.headlinehive.model.NewsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class NewsService {

    private static final Logger logger = LoggerFactory.getLogger(NewsService.class);

    @Value("${newsapi.key}")
    private String apiKey;

    @Value("${newsapi.url}")
    private String baseUrl;

    @Value("${newsapi.country}")
    private String country;

    private final RestTemplate restTemplate;

    public NewsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Article> fetchNews(String category) {
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException("API Key is missing. Please check your application.properties.");
        }

        StringBuilder urlBuilder = new StringBuilder(baseUrl)
                .append("?country=").append(country);

        // If category is null or "all", don't append the category parameter
        if (category != null && !category.equalsIgnoreCase("all")) {
            urlBuilder.append("&category=").append(category);
        }

        urlBuilder.append("&apiKey=").append(apiKey);

        String url = urlBuilder.toString();
        logger.info("Fetching news from URL: {}", url);

        NewsResponse response = null;
        try {
            response = restTemplate.getForObject(url, NewsResponse.class);
        } catch (Exception e){
            logger.error("Error fetching news from API", e);
        }

        if (response == null) {
            logger.warn("NewsResponse is null");
            return List.of();
        }

        logger.info("API status: {}, totalResults: {}", response.getStatus(), response.getTotalResults());

        if (response.getArticles() == null || response.getArticles().isEmpty()) {
            logger.warn("No articles found in response");
            return List.of();
        }

        return response.getArticles();
    }
}
