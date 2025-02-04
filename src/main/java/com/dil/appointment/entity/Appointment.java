package com.dil.appointment.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 预约实体类
 */
@Data
public class Appointment {
    private Long id;
    private Long userId;
    private Long doctorId;
    private LocalDateTime scheduleTime;
    private String status;
    private String paymentStatus;
    private String form;
    private String content;
    private String time;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 