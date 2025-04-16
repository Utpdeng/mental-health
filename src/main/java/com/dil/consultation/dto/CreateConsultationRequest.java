package com.dil.consultation.dto;

import lombok.Data;

/**
 * 创建咨询记录请求DTO
 */
@Data
public class CreateConsultationRequest {
    /**
     * 预约ID
     */
    private Long appointmentId;

    /**
     * 医生ID
     */
    private Long doctorId;

    /**
     * 用户ID
     */
    private Long userId;
} 