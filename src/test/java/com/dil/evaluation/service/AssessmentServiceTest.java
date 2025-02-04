package com.dil.evaluation.service;

import com.dil.common.exception.BusinessException;
import com.dil.evaluation.dto.AssessmentResponse;
import com.dil.evaluation.dto.CreateAssessmentRequest;
import com.dil.evaluation.dto.SubmitAnswerRequest;
import com.dil.evaluation.model.Assessment;
import com.dil.evaluation.model.AssessmentResult;
import com.dil.evaluation.util.TestDataUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class AssessmentServiceTest {
    
    @Autowired
    private AssessmentService assessmentService;
    
    @Autowired
    private MongoTemplate mongoTemplate;
    
    @BeforeEach
    void setUp() {
        // 清空测试数据
        mongoTemplate.dropCollection(Assessment.class);
        mongoTemplate.dropCollection(AssessmentResult.class);
    }
    
    @Test
    void testCreateAssessment() {
        CreateAssessmentRequest request = TestDataUtil.createTestAssessmentRequest();
        String id = assessmentService.createAssessment(1L, request);
        
        assertNotNull(id);
        
        Assessment saved = mongoTemplate.findById(id, Assessment.class);
        assertNotNull(saved);
        assertEquals(request.getTitle(), saved.getTitle());
        assertEquals(request.getType(), saved.getType());
        assertEquals("pending", saved.getStatus());
        assertEquals(1L, saved.getCreatorId());
    }
    
    @Test
    void testUpdateAssessment() {
        // 先创建测评
        CreateAssessmentRequest createRequest = TestDataUtil.createTestAssessmentRequest();
        String id = assessmentService.createAssessment(1L, createRequest);
        
        // 修改测评
        CreateAssessmentRequest updateRequest = TestDataUtil.createTestAssessmentRequest();
        updateRequest.setTitle("更新后的标题");
        assessmentService.updateAssessment(id, updateRequest);
        
        Assessment updated = mongoTemplate.findById(id, Assessment.class);
        assertEquals("更新后的标题", updated.getTitle());
    }
    
    @Test
    void testReviewAssessment() {
        // 先创建测评
        CreateAssessmentRequest request = TestDataUtil.createTestAssessmentRequest();
        String id = assessmentService.createAssessment(1L, request);
        
        // 审核测评
        assessmentService.reviewAssessment(id, "approved", "通过审核", 2L);
        
        Assessment reviewed = mongoTemplate.findById(id, Assessment.class);
        assertEquals("approved", reviewed.getStatus());
        assertEquals("通过审核", reviewed.getReviewComment());
        assertEquals(2L, reviewed.getReviewerId());
    }
    
    @Test
    void testDeleteAssessment() {
        // 先创建测评
        CreateAssessmentRequest request = TestDataUtil.createTestAssessmentRequest();
        String id = assessmentService.createAssessment(1L, request);
        
        // 删除测评
        assessmentService.deleteAssessment(id);
        
        Assessment deleted = mongoTemplate.findById(id, Assessment.class);
        assertNull(deleted);
    }
    
    @Test
    void testGetAssessments() {
        // 创建两个测评
        CreateAssessmentRequest request1 = TestDataUtil.createTestAssessmentRequest();
        CreateAssessmentRequest request2 = TestDataUtil.createTestAssessmentRequest();
        request2.setType("STRESS");
        
        assessmentService.createAssessment(1L, request1);
        assessmentService.createAssessment(1L, request2);
        
        // 测试查询全部
        List<AssessmentResponse> all = assessmentService.getAssessments(null, null);
        assertEquals(2, all.size());
        
        // 测试按类型查询
        List<AssessmentResponse> emotion = assessmentService.getAssessments("EMOTION", null);
        assertEquals(1, emotion.size());
        assertEquals("EMOTION", emotion.get(0).getType());
    }
    
    @Test
    void testGetAssessmentDetail() {
        // 创建测评
        CreateAssessmentRequest request = TestDataUtil.createTestAssessmentRequest();
        String id = assessmentService.createAssessment(1L, request);
        
        // 查询详情
        AssessmentResponse detail = assessmentService.getAssessmentDetail(id);
        
        assertNotNull(detail);
        assertEquals(request.getTitle(), detail.getTitle());
        assertEquals(request.getType(), detail.getType());
        assertNotNull(detail.getQuestions());
        assertEquals(1, detail.getQuestions().size());
    }
    
    @Test
    void testSubmitInvalidAnswer() {
        // 创建测评
        CreateAssessmentRequest request = TestDataUtil.createTestAssessmentRequest();
        String id = assessmentService.createAssessment(1L, request);
        
        // 提交无效答案
        SubmitAnswerRequest submitRequest = new SubmitAnswerRequest();
        submitRequest.setAssessmentId(id);
        submitRequest.setAnswers(new ArrayList<>());
        
        assertThrows(BusinessException.class, () -> {
            assessmentService.submitAnswer(1L, submitRequest);
        });
    }
    
    @Test
    void testSubmitAnswerToUnapprovedAssessment() {
        // 创建测评（状态为pending）
        CreateAssessmentRequest request = TestDataUtil.createTestAssessmentRequest();
        String id = assessmentService.createAssessment(1L, request);
        
        // 尝试提交答案
        SubmitAnswerRequest submitRequest = new SubmitAnswerRequest();
        submitRequest.setAssessmentId(id);
        List<SubmitAnswerRequest.AnswerItem> answers = new ArrayList<>();
        SubmitAnswerRequest.AnswerItem answer = new SubmitAnswerRequest.AnswerItem();
        answer.setQuestionIndex(0);
        answer.setSelectedOption("A");
        answers.add(answer);
        submitRequest.setAnswers(answers);
        
        assertThrows(BusinessException.class, () -> {
            assessmentService.submitAnswer(1L, submitRequest);
        });
    }
    
    @Test
    void testSubmitAnswer() {
        // 创建测试测评
        CreateAssessmentRequest createRequest = TestDataUtil.createTestAssessmentRequest();
        String assessmentId = assessmentService.createAssessment(1L, createRequest);
        
        // 审核通过
        assessmentService.reviewAssessment(assessmentId, "approved", "通过", 1L);
        
        // 提交答案
        SubmitAnswerRequest submitRequest = new SubmitAnswerRequest();
        submitRequest.setAssessmentId(assessmentId);
        
        List<SubmitAnswerRequest.AnswerItem> answers = new ArrayList<>();
        SubmitAnswerRequest.AnswerItem answer = new SubmitAnswerRequest.AnswerItem();
        answer.setQuestionIndex(0);
        answer.setSelectedOption("A");
        answers.add(answer);
        
        submitRequest.setAnswers(answers);
        
        // 测试提交
        AssessmentResult result = assessmentService.submitAnswer(1L, submitRequest);
        
        assertNotNull(result.getId());
        assertEquals(assessmentId, result.getAssessmentId());
        assertEquals(1L, result.getUserId());
        assertEquals(0, result.getTotalScore());
        assertEquals(1, result.getAnswers().size());
        assertEquals("情绪状态良好", result.getEvaluation());
    }
    
    @Test
    void testGetUserResults() {
        // 创建测试数据
        AssessmentResult result = new AssessmentResult();
        result.setUserId(1L);
        result.setAssessmentId("test");
        result.setTotalScore(0);
        result.setEvaluation("测试结果");
        result.setCompletedAt(LocalDateTime.now());
        mongoTemplate.save(result);
        
        // 测试查询
        List<AssessmentResult> results = assessmentService.getUserResults(1L);
        
        assertEquals(1, results.size());
        assertEquals("test", results.get(0).getAssessmentId());
    }
    
    @Test
    void testGetResultDetail() {
        // 创建测试数据
        AssessmentResult result = new AssessmentResult();
        result.setUserId(1L);
        result.setAssessmentId("test");
        result.setTotalScore(0);
        result.setEvaluation("测试结果");
        result.setCompletedAt(LocalDateTime.now());
        AssessmentResult saved = mongoTemplate.save(result);
        
        // 测试查询
        AssessmentResult detail = assessmentService.getResultDetail(saved.getId());
        
        assertNotNull(detail);
        assertEquals("test", detail.getAssessmentId());
    }
} 