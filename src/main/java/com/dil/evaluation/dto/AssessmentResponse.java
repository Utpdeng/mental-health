package com.dil.evaluation.dto;

import com.dil.evaluation.model.Assessment;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class AssessmentResponse {
    private String id;
    private String title;
    private String type;
    private String description;
    private Integer estimatedTime;
    private String status;
    private LocalDateTime createdAt;
    private String creatorName;
    // 仅在查询详情时返回题目
    private List<Assessment.Question> questions;
} 