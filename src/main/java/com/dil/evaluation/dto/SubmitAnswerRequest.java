package com.dil.evaluation.dto;

import lombok.Data;
import java.util.List;

@Data
public class SubmitAnswerRequest {
    /**
     * 测评ID
     */
    private String assessmentId;
    
    /**
     * 答题记录
     */
    private List<AnswerItem> answers;
    
    @Data
    public static class AnswerItem {
        /**
         * 题目序号（从0开始）
         */
        private Integer questionIndex;
        
        /**
         * 选择的选项key
         */
        private String selectedOption;
    }
} 