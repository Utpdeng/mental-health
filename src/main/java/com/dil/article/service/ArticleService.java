package com.dil.article.service;

import com.dil.article.entity.Article;

import java.util.List;

public interface ArticleService {
    Article createArticle(Article article);
    Article updateArticle(String id, Article article);
    void deleteArticle(String id);
    Article getArticleById(String id);
    List<Article> getAllArticles();
    List<Article> searchByTitle(String title);
    List<Article> findByCategory(String category);
    List<Article> searchByTitleAndCategory(String title, String category);
    List<Article> getArticlesWithPagination(int pageNo, int pageSize);
    void reviewArticle(String id, String reviewStatus);
}