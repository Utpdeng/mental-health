package com.dil.psychologist.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 心理医生列表响应DTO
 */
@Data
public class PsychologistListResponse {
    /**
     * 心理医生ID
     */
    private Long psychologistId;

    /**
     * 关联的用户ID
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像地址
     */
    private String avatarUrl;

    /**
     * 职称
     */
    private String title;

    /**
     * 专长领域
     */
    private String specialty;

    /**
     * 个人简介
     */
    private String introduction;

    /**
     * 从业年限
     */
    private Integer workYears;

    /**
     * 咨询费用/小时
     */
    private BigDecimal consultationFee;

    /**
     * 雇佣状态
     */
    private String employmentStatus;

    /**
     * 性别
     */
    private String gender;

    /**
     * 所在城市
     */
    private String location;

    /**
     * 每日最大预约数
     */
    private Integer maxAppointmentsPerDay;
}
