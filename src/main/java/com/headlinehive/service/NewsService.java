package com.headlinehive.service;

import com.headlinehive.model.Article;
import com.headlinehive.model.NewsResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Service
public class NewsService {

    private final RestTemplate restTemplate;

    @Value("${newsapi.url}")
    private String apiUrl;

    @Value("${newsapi.country}")
    private String country;

    @Value("${newsapi.key}")
    private String apiKey;

    @Value("${newsapi.everything.url}")
    private String everythingUrl;

    @Value("${newsapi.everything.query}")
    private String everythingQuery;

    public NewsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Article> getTopArticles() {
        System.out.println("🔍 NewsService#getTopArticles() called");

        // Top Headlines URL
        String url = String.format("%s?country=%s&apiKey=%s", apiUrl, country, apiKey);
        System.out.println("Calling NewsAPI: " + url);

        ResponseEntity<NewsResponse> resp = restTemplate.getForEntity(url, NewsResponse.class);
        NewsResponse news = resp.getBody();

        if (news == null || news.getArticles() == null || news.getArticles().isEmpty()) {
            System.out.println("⚠️ No top headlines, fallback to Everything API");

            // Fallback to Everything API
            String everythingUrl = String.format("%s?q=%s&apiKey=%s", this.everythingUrl, everythingQuery, apiKey);
            System.out.println("Calling NewsAPI Everything: " + everythingUrl);
            ResponseEntity<NewsResponse> allResp = restTemplate.getForEntity(everythingUrl, NewsResponse.class);
            news = allResp.getBody();
        }

        if (news == null || news.getArticles() == null) {
            System.out.println("⚠️ Articles list is null");
            return Collections.emptyList();
        }

        System.out.println("🔍 totalResults = " + news.getTotalResults());
        System.out.println("🔍 articles.size = " + news.getArticles().size());
        news.getArticles().forEach(a -> System.out.println(" • " + a.getTitle()));

        return news.getArticles();
    }
}
