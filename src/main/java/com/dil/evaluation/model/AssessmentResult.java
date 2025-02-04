package com.dil.evaluation.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document(collection = "assessment_results")
public class AssessmentResult {
    @Id
    private String id;
    
    /**
     * 测评ID
     */
    private String assessmentId;
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 答题记录
     */
    private List<Answer> answers;
    
    /**
     * 总分
     */
    private Integer totalScore;
    
    /**
     * 评估结果
     */
    private String evaluation;
    
    /**
     * 完成时间
     */
    private LocalDateTime completedAt;

    @Data
    public static class Answer {
        /**
         * 题目序号
         */
        private Integer questionIndex;
        
        /**
         * 选择的选项
         */
        private String selectedOption;
        
        /**
         * 得分
         */
        private Integer score;
    }
} 