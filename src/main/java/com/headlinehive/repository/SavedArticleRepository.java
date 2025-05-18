package com.headlinehive.repository;

import com.headlinehive.model.SavedArticle;
import com.headlinehive.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SavedArticleRepository extends JpaRepository<SavedArticle, Long> {
    List<SavedArticle> findByUser(User user);
}
