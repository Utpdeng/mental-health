package com.dil.consultation.controller;

import com.dil.consultation.dto.CreateConsultationRequest;
import com.dil.consultation.dto.UpdateConsultationRequest;
import com.dil.consultation.dto.ConsultationResponse;
import com.dil.consultation.service.ConsultationService;
import com.dil.common.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 咨询记录控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/consultations")
public class ConsultationController {
    
    @Autowired
    private ConsultationService consultationService;

    /**
     * 创建咨询记录
     */
    @PostMapping("/create")
    public Result<Long> createConsultation(@RequestBody CreateConsultationRequest request) {
        log.info("收到创建咨询记录请求，预约ID：{}", request.getAppointmentId());
        Long consultationId = consultationService.createConsultation(request);
        return Result.success(consultationId);
    }

    /**
     * 更新咨询记录
     */
    @PostMapping("/update")
    public Result<?> updateConsultation(@RequestBody UpdateConsultationRequest request) {
        log.info("收到更新咨询记录请求，咨询ID：{}", request.getId());
        consultationService.updateConsultation(request);
        return Result.success(null);
    }

    /**
     * 查询咨询记录列表
     */
    @GetMapping("/list")
    public Result<List<ConsultationResponse>> getConsultationList(
            @RequestParam(required = false) Long doctorId,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Long appointmentId,
            @RequestParam(required = false) String form) {
        log.info("收到查询咨询记录列表请求，医生ID：{}，用户ID：{}，预约ID：{}，咨询形式：{}", doctorId, userId, appointmentId, form);
        List<ConsultationResponse> consultations = consultationService.getConsultationList(doctorId, userId, appointmentId, form);
        return Result.success(consultations);
    }

    /**
     * 查询咨询记录详情
     *
     * @param id 咨询记录ID
     * @return 咨询记录详情
     */
    @GetMapping("/{id}")
    public Result<ConsultationResponse> getConsultationById(@PathVariable Long id) {
        log.info("收到查询咨询记录详情请求，ID：{}", id);
        ConsultationResponse consultation = consultationService.getConsultationById(id);
        return Result.success(consultation);
    }
} 