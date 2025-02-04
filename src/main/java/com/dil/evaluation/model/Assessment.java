package com.dil.evaluation.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 心理测评模型
 */
@Data
@Document(collection = "assessments")
public class Assessment {
    @Id
    private String id;
    
    /**
     * 测评标题
     */
    private String title;
    
    /**
     * 测评类型
     */
    private String type;
    
    /**
     * 测评描述
     */
    private String description;
    
    /**
     * 建议用时（分钟）
     */
    private Integer estimatedTime;
    
    /**
     * 题目列表
     */
    private List<Question> questions;
    
    /**
     * 创建者ID（心理医生ID）
     */
    private Long creatorId;
    
    /**
     * 审核状态（pending/approved/rejected）
     */
    private String status;
    
    /**
     * 审核意见
     */
    private String reviewComment;
    
    /**
     * 审核人ID
     */
    private Long reviewerId;
    
    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
    
    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;

    @Data
    public static class Question {
        /**
         * 题目内容
         */
        private String content;
        
        /**
         * 选项列表
         */
        private List<Option> options;
        
        /**
         * 分值规则
         */
        private List<ScoreRule> scoreRules;
    }

    @Data
    public static class Option {
        /**
         * 选项标识
         */
        private String key;
        
        /**
         * 选项内容
         */
        private String content;
    }

    @Data
    public static class ScoreRule {
        /**
         * 选项标识
         */
        private String optionKey;
        
        /**
         * 对应分值
         */
        private Integer score;
    }
} 