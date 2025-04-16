package com.dil.psychologist.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 更新心理医生请求DTO
 */
@Data
public class UpdatePsychologistRequest {
    /**
     * 心理医生ID
     */
    private Long psychologistId;

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
     * 是否删除
     */
    private Boolean isDelete;
} 