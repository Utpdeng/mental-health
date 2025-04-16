package com.dil.evaluation.dto;

import com.dil.evaluation.model.Assessment;
import lombok.Data;

import java.util.List;

@Data
public class CreateAssessmentRequest {
    private String title;
    private String type;
    private String description;
    private Integer estimatedTime;
    private List<Assessment.Question> questions;
} 