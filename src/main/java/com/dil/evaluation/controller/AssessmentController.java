package com.dil.evaluation.controller;

import com.dil.common.response.Result;
import com.dil.evaluation.dto.CreateAssessmentRequest;
import com.dil.evaluation.dto.AssessmentResponse;
import com.dil.evaluation.dto.SubmitAnswerRequest;
import com.dil.evaluation.model.AssessmentResult;
import com.dil.evaluation.service.AssessmentService;
import com.dil.common.context.UserContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/assessments")
public class AssessmentController {
    
    @Autowired
    private AssessmentService assessmentService;
    
    @PostMapping("/create")
    public Result<String> createAssessment(@RequestBody CreateAssessmentRequest request) {
        UserContext.UserInfo userInfo = UserContext.getUser();
        String id = assessmentService.createAssessment(userInfo.getUserId(), request);
        return Result.success(id);
    }
    
    @PostMapping("/{id}/update")
    public Result<?> updateAssessment(@PathVariable String id, @RequestBody CreateAssessmentRequest request) {
        assessmentService.updateAssessment(id, request);
        return Result.success(null);
    }
    
    @PostMapping("/{id}/review")
    public Result<?> reviewAssessment(
            @PathVariable String id,
            @RequestParam String status,
            @RequestParam String comment) {
        Long reviewerId = UserContext.getUser().getUserId();
        assessmentService.reviewAssessment(id, status, comment, reviewerId);
        return Result.success(null);
    }
    
    @DeleteMapping("/{id}")
    public Result<?> deleteAssessment(@PathVariable String id) {
        assessmentService.deleteAssessment(id);
        return Result.success(null);
    }
    
    @GetMapping("/list")
    public Result<List<AssessmentResponse>> getAssessments(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String status) {
        List<AssessmentResponse> assessments = assessmentService.getAssessments(type, status);
        return Result.success(assessments);
    }
    
    @GetMapping("/{id}")
    public Result<AssessmentResponse> getAssessmentDetail(@PathVariable String id) {
        AssessmentResponse assessment = assessmentService.getAssessmentDetail(id);
        return Result.success(assessment);
    }
    
    /**
     * 提交答案
     */
    @PostMapping("/submit")
    public Result<AssessmentResult> submitAnswer(@RequestBody SubmitAnswerRequest request) {
        Long userId = UserContext.getUser().getUserId();
        AssessmentResult result = assessmentService.submitAnswer(userId, request);
        return Result.success(result);
    }
    
    /**
     * 获取用户的测评历史
     */
    @GetMapping("/results")
    public Result<List<AssessmentResult>> getUserResults() {
        Long userId = UserContext.getUser().getUserId();
        List<AssessmentResult> results = assessmentService.getUserResults(userId);
        return Result.success(results);
    }
    
    /**
     * 获取测评结果详情
     */
    @GetMapping("/results/{resultId}")
    public Result<AssessmentResult> getResultDetail(@PathVariable String resultId) {
        AssessmentResult result = assessmentService.getResultDetail(resultId);
        return Result.success(result);
    }
} 