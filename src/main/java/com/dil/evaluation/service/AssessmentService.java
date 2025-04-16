package com.dil.evaluation.service;

import com.dil.common.exception.BusinessException;
import com.dil.evaluation.dto.CreateAssessmentRequest;
import com.dil.evaluation.dto.AssessmentResponse;
import com.dil.evaluation.dto.SubmitAnswerRequest;
import com.dil.evaluation.model.Assessment;
import com.dil.evaluation.model.AssessmentResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AssessmentService {
    
    @Autowired
    private MongoTemplate mongoTemplate;
    
    /**
     * 创建测评
     */
    public String createAssessment(Long creatorId, CreateAssessmentRequest request) {
        Assessment assessment = new Assessment();
        BeanUtils.copyProperties(request, assessment);
        assessment.setCreatorId(creatorId);
        assessment.setStatus("pending");
        assessment.setCreatedAt(LocalDateTime.now());
        assessment.setUpdatedAt(LocalDateTime.now());
        
        Assessment saved = mongoTemplate.save(assessment);
        return saved.getId();
    }
    
    /**
     * 更新测评
     */
    public void updateAssessment(String id, CreateAssessmentRequest request) {
        Assessment assessment = mongoTemplate.findById(id, Assessment.class);
        if (assessment == null) {
            throw new BusinessException("测评不存在");
        }
        
        if (!"pending".equals(assessment.getStatus())) {
            throw new BusinessException("只能修改待审核的测评");
        }
        
        BeanUtils.copyProperties(request, assessment);
        assessment.setUpdatedAt(LocalDateTime.now());
        mongoTemplate.save(assessment);
    }
    
    /**
     * 审核测评
     */
    public void reviewAssessment(String id, String status, String comment, Long reviewerId) {
        Assessment assessment = mongoTemplate.findById(id, Assessment.class);
        if (assessment == null) {
            throw new BusinessException("测评不存在");
        }
        
        assessment.setStatus(status);
        assessment.setReviewComment(comment);
        assessment.setReviewerId(reviewerId);
        assessment.setUpdatedAt(LocalDateTime.now());
        mongoTemplate.save(assessment);
    }
    
    /**
     * 删除测评
     */
    public void deleteAssessment(String id) {
        Assessment assessment = mongoTemplate.findById(id, Assessment.class);
        if (assessment == null) {
            throw new BusinessException("测评不存在");
        }
        
        mongoTemplate.remove(assessment);
    }
    
    /**
     * 查询测评列表
     */
    public List<AssessmentResponse> getAssessments(String type, String status) {
        Query query = new Query();
        if (StringUtils.hasText(type)) {
            query.addCriteria(Criteria.where("type").is(type));
        }
        if (StringUtils.hasText(status)) {
            query.addCriteria(Criteria.where("status").is(status));
        }
        
        return mongoTemplate.find(query, Assessment.class)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    /**
     * 查询测评详情
     */
    public AssessmentResponse getAssessmentDetail(String id) {
        Assessment assessment = mongoTemplate.findById(id, Assessment.class);
        if (assessment == null) {
            throw new BusinessException("测评不存在");
        }
        
        return convertToResponse(assessment);
    }
    
    private AssessmentResponse convertToResponse(Assessment assessment) {
        AssessmentResponse response = new AssessmentResponse();
        BeanUtils.copyProperties(assessment, response);
        // 查询创建者信息
        return response;
    }
    
    /**
     * 提交答案并计算结果
     */
    @Transactional
    public AssessmentResult submitAnswer(Long userId, SubmitAnswerRequest request) {
        // 获取测评信息
        Assessment assessment = mongoTemplate.findById(request.getAssessmentId(), Assessment.class);
        if (assessment == null) {
            throw new BusinessException("测评不存在");
        }
        
        if (!"approved".equals(assessment.getStatus())) {
            throw new BusinessException("该测评尚未通过审核");
        }
        
        // 验证答案数量
        if (request.getAnswers().size() != assessment.getQuestions().size()) {
            throw new BusinessException("答题数量不正确");
        }
        
        // 计算得分
        List<AssessmentResult.Answer> answers = new ArrayList<>();
        int totalScore = 0;
        
        for (SubmitAnswerRequest.AnswerItem item : request.getAnswers()) {
            Assessment.Question question = assessment.getQuestions().get(item.getQuestionIndex());
            
            // 查找对应的分值规则
            Integer score = question.getScoreRules().stream()
                    .filter(rule -> rule.getOptionKey().equals(item.getSelectedOption()))
                    .map(Assessment.ScoreRule::getScore)
                    .findFirst()
                    .orElseThrow(() -> new BusinessException("无效的选项"));
            
            AssessmentResult.Answer answer = new AssessmentResult.Answer();
            answer.setQuestionIndex(item.getQuestionIndex());
            answer.setSelectedOption(item.getSelectedOption());
            answer.setScore(score);
            answers.add(answer);
            
            totalScore += score;
        }
        
        // 生成评估结果
        String evaluation = generateEvaluation(assessment.getType(), totalScore);
        
        // 保存结果
        AssessmentResult result = new AssessmentResult();
        result.setAssessmentId(request.getAssessmentId());
        result.setUserId(userId);
        result.setAnswers(answers);
        result.setTotalScore(totalScore);
        result.setEvaluation(evaluation);
        result.setCompletedAt(LocalDateTime.now());
        
        return mongoTemplate.save(result);
    }
    
    /**
     * 获取用户的测评历史
     */
    public List<AssessmentResult> getUserResults(Long userId) {
        Query query = Query.query(Criteria.where("userId").is(userId));
        return mongoTemplate.find(query, AssessmentResult.class);
    }
    
    /**
     * 获取测评结果详情
     */
    public AssessmentResult getResultDetail(String resultId) {
        AssessmentResult result = mongoTemplate.findById(resultId, AssessmentResult.class);
        if (result == null) {
            throw new BusinessException("测评结果不存在");
        }
        return result;
    }
    
    /**
     * 根据得分生成评估结果
     */
    private String generateEvaluation(String type, int totalScore) {
        // 这里简化处理，实际应该根据不同类型的测评有不同的评分标准
        if ("EMOTION".equals(type)) {
            if (totalScore <= 5) {
                return "情绪状态良好";
            } else if (totalScore <= 10) {
                return "存在轻微情绪问题，建议适当关注";
            } else {
                return "情绪状态欠佳，建议咨询心理医生";
            }
        }
        // 可以添加其他类型的评估标准
        return "暂无评估结果";
    }
} 