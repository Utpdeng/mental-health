package com.dil.consultation.dto;

import lombok.Data;

/**
 * 更新咨询记录请求DTO
 */
@Data
public class UpdateConsultationRequest {
    /**
     * 咨询
     */
    private Long id;

    /**
     * 反馈内容
     */
    private String feedback;
} 