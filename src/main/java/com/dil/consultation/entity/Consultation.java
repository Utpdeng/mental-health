package com.dil.consultation.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 咨询记录实体类
 */
@Data
public class Consultation {
    private Long id;
    private Long appointmentId;
    private Long doctorId;
    private Long userId;
    private String chatLog;
    private String feedback;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}