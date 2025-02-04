package com.dil.article.controller;

import com.dil.article.entity.Article;
import com.dil.article.service.ArticleService;
import com.dil.common.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 发布文章
     */
    @PostMapping("create")
    public Result<Article> createArticle(@RequestBody Article article) {
        return Result.success(articleService.createArticle(article));
    }

    /**
     * 更新文章
     */
    @PutMapping("/{id}")
    public Result<Article> updateArticle(@PathVariable String id, @RequestBody Article article) {
        return Result.success(articleService.updateArticle(id, article));
    }

    /**
     * 删除文章
     */
    @DeleteMapping("/{id}")
    public Result<Void> deleteArticle(@PathVariable String id) {
        articleService.deleteArticle(id);
        return Result.success(null);
    }

    /**
     * 根据id查询文章
     */
    @GetMapping("/{id}")
    public Result<Article> getArticle(@PathVariable String id) {
        Article article = articleService.getArticleById(id);
        return Result.success(article);
    }

    /**
     * 查询所有文章
     */
    @GetMapping("list")
    public Result<List<Article>> getAllArticles() {
        return Result.success(articleService.getAllArticles());
    }

    /**
     * 根据标题和分类查询文章
     */
    @GetMapping("/search")
    public Result<List<Article>> searchArticles(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String category) {
        if (StringUtils.hasText(title) && StringUtils.hasText(category)) {
            return Result.success(articleService.searchByTitleAndCategory(title, category));
        } else if (StringUtils.hasText(title)) {
            return Result.success(articleService.searchByTitle(title));
        } else if (StringUtils.hasText(category)) {
            return Result.success(articleService.findByCategory(category));
        } else {
            return Result.success(articleService.getAllArticles());
        }
    }

    /**
     * 分页查询文章
     */
    @GetMapping("/page")
    public Result<List<Article>> getArticlesWithPagination(
            @RequestParam(defaultValue = "1") int pageNo,
            @RequestParam(defaultValue = "6") int pageSize) {
        return Result.success(articleService.getArticlesWithPagination(pageNo, pageSize));
    }

    @PostMapping("/review")
    public Result<Void> reviewArticle(@RequestParam String id, @RequestParam String reviewStatus) {
        articleService.reviewArticle(id, reviewStatus);
        return Result.success(null);
    }

} 