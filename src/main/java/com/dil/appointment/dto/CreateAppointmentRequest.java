package com.dil.appointment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 创建预约请求DTO
 */
@Data
public class CreateAppointmentRequest {
    private Long userId;
    private Long doctorId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime scheduleTime;
    private String form;
    private String content;
    private String time;
} 