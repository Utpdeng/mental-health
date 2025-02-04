package com.dil.evaluation.util;

import com.dil.evaluation.dto.CreateAssessmentRequest;
import com.dil.evaluation.model.Assessment;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 测试数据工具类
 */
public class TestDataUtil {
    
    /**
     * 创建测试用的测评请求
     */
    public static CreateAssessmentRequest createTestAssessmentRequest() {
        CreateAssessmentRequest request = new CreateAssessmentRequest();
        request.setTitle("情绪健康测评");
        request.setType("EMOTION");
        request.setDescription("测试您的情绪健康状况");
        request.setEstimatedTime(15);
        request.setQuestions(createTestQuestions());
        return request;
    }
    
    /**
     * 创建测试用的题目列表
     */
    public static List<Assessment.Question> createTestQuestions() {
        List<Assessment.Question> questions = new ArrayList<>();
        
        Assessment.Question question = new Assessment.Question();
        question.setContent("您最近两周内感到心情低落的频率是？");
        
        List<Assessment.Option> options = Arrays.asList(
            createOption("A", "从不"),
            createOption("B", "偶尔"),
            createOption("C", "经常"),
            createOption("D", "总是")
        );
        question.setOptions(options);
        
        List<Assessment.ScoreRule> scoreRules = Arrays.asList(
            createScoreRule("A", 0),
            createScoreRule("B", 1),
            createScoreRule("C", 2),
            createScoreRule("D", 3)
        );
        question.setScoreRules(scoreRules);
        
        questions.add(question);
        return questions;
    }
    
    /**
     * 创建测试用的选项
     */
    private static Assessment.Option createOption(String key, String content) {
        Assessment.Option option = new Assessment.Option();
        option.setKey(key);
        option.setContent(content);
        return option;
    }
    
    /**
     * 创建测试用的评分规则
     */
    private static Assessment.ScoreRule createScoreRule(String optionKey, Integer score) {
        Assessment.ScoreRule rule = new Assessment.ScoreRule();
        rule.setOptionKey(optionKey);
        rule.setScore(score);
        return rule;
    }
} 