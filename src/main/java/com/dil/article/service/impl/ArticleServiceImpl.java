package com.dil.article.service.impl;

import com.dil.article.entity.Article;
import com.dil.article.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Article createArticle(Article article) {
        article.setCreateTime(LocalDateTime.now());
        article.setUpdateTime(LocalDateTime.now());
        article.setReview_status("pending");
        article.setIsDeleted(false);
        return mongoTemplate.save(article);
    }

    @Override
    public Article updateArticle(String id, Article article) {
        Query query = new Query(Criteria.where("id").is(id).and("isDeleted").is(false));
        Update update = new Update()
                .set("title", article.getTitle())
                .set("category", article.getCategory())
                .set("content", article.getContent())
                .set("review_status", article.getReview_status())
                .set("updateTime", LocalDateTime.now());
        
        mongoTemplate.updateFirst(query, update, Article.class);
        return getArticleById(id);
    }

    @Override
    public void deleteArticle(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        Update update = new Update().set("isDeleted", true);
        mongoTemplate.updateFirst(query, update, Article.class);
    }

    @Override
    public Article getArticleById(String id) {
        Query query = new Query(Criteria.where("id").is(id).and("isDeleted").is(false));
        return mongoTemplate.findOne(query, Article.class);
    }

    @Override
    public List<Article> getAllArticles() {
        Query query = new Query(Criteria.where("isDeleted").is(false));
        return mongoTemplate.find(query, Article.class);
    }

    @Override
    public List<Article> searchByTitle(String title) {
        Query query = new Query();
        if (StringUtils.hasText(title)) {
            query.addCriteria(Criteria.where("title").regex(title, "i"))
                 .addCriteria(Criteria.where("isDeleted").is(false));
        }
        return mongoTemplate.find(query, Article.class);
    }

    @Override
    public List<Article> findByCategory(String category) {
        Query query = new Query(Criteria.where("category").is(category)
                .and("isDeleted").is(false));
        return mongoTemplate.find(query, Article.class);
    }

    @Override
    public List<Article> searchByTitleAndCategory(String title, String category) {
        Query query = new Query();
        Criteria criteria = new Criteria();
        
        if (StringUtils.hasText(title)) {
            criteria.and("title").regex(title, "i");
        }
        if (StringUtils.hasText(category)) {
            criteria.and("category").is(category);
        }
        
        query.addCriteria(criteria)
             .addCriteria(Criteria.where("isDeleted").is(false));
             
        return mongoTemplate.find(query, Article.class);
    }

    @Override
    public List<Article> getArticlesWithPagination(int pageNo, int pageSize) {
        Query query = new Query(Criteria.where("isDeleted").is(false));
        // 设置排序（按创建时间倒序）
        query.with(Sort.by(Sort.Direction.DESC, "createTime"));
        // 设置分页
        query.skip((long) (pageNo - 1) * pageSize).limit(pageSize);
        
        return mongoTemplate.find(query, Article.class);
    }

    @Override
    public void reviewArticle(String id, String reviewStatus) {

    }
} 