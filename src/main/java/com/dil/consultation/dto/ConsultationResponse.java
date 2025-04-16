package com.dil.consultation.dto;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 咨询记录响应DTO
 */
@Data
public class ConsultationResponse {
    private Long id;
    private Long appointmentId;
    private Long doctorId;
    private String doctorName;
    private Long userId;
    private String username;
    private String gender;
    private String chatLog;
    private String form;
    private String time;
    private String avatarUrl;
    private String doctorAvatar;
    private String status;
    private String feedback;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}