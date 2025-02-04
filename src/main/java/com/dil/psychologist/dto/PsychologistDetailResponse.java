package com.dil.psychologist.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 心理医生详情响应DTO
 */
@Data
public class PsychologistDetailResponse {
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
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String phone;

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
     * 资质证书
     */
    private String qualification;

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
     * 入职日期
     */
    private LocalDate employmentDate;

    /**
     * 离职日期
     */
    private LocalDate resignationDate;

    /**
     * 休假开始日期
     */
    private LocalDate leaveStartDate;

    /**
     * 休假结束日期
     */
    private LocalDate leaveEndDate;

    /**
     * 休假原因
     */
    private String leaveReason;

    /**
     * 每日最大预约数
     */
    private Integer maxAppointmentsPerDay;

    /**
     * 性别
     */
    private String gender;

    /**
     * 出生日期
     */
    private LocalDate birthdate;

    /**
     * 所在城市
     */
    private String location;

    /**
     * 用户偏好设置
     */
    private String preferences;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
}
