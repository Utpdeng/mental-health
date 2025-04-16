package com.dil.appointment.dto;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 预约响应DTO
 */
@Data
public class AppointmentResponse {
    private Long id;
    private Long userId;
    private Long doctorId;
    private String doctorName;
    private String doctorTitle;
    private String userName;
    private String userAvatar;
    private LocalDateTime scheduleTime;
    private String status;
    private String paymentStatus;
    private String form;
    private String content;
    private String time;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 