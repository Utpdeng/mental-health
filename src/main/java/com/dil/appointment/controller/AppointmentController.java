package com.dil.appointment.controller;

import com.dil.appointment.dto.CreateAppointmentRequest;
import com.dil.appointment.dto.AppointmentResponse;
import com.dil.appointment.service.AppointmentService;
import com.dil.common.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 预约控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {
    
    @Autowired
    private AppointmentService appointmentService;

    /**
     * 创建预约
     */
    @PostMapping("/create")
    public Result<Long> createAppointment(@RequestBody CreateAppointmentRequest request) {
        Long userId = request.getUserId();
        log.info("收到创建预约请求，用户ID：{}", userId);
        Long appointmentId = appointmentService.createAppointment(userId, request);
        return Result.success(appointmentId);
    }

    /**
     * 查询预约列表
     */
    @GetMapping("/list")
    public Result<List<AppointmentResponse>> getAppointmentList(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Long doctorId,
            @RequestParam(required = false) String status) {
        log.info("收到查询预约列表请求，用户ID：{}，医生ID：{}，状态：{}", userId, doctorId, status);
        List<AppointmentResponse> appointments = appointmentService.getAppointmentList(userId, doctorId, status);
        return Result.success(appointments);
    }

    /**
     * 查询预约详情
     */
    @GetMapping("/{id}")
    public Result<AppointmentResponse> getAppointmentDetail(@PathVariable Long id) {
        log.info("收到查询预约详情请求，预约ID：{}", id);
        AppointmentResponse appointment = appointmentService.getAppointmentDetail(id);
        return Result.success(appointment);
    }

    /**
     * 修改预约状态
     */
    @PutMapping("/{id}")
    public Result<Integer> updateAppointmentStatus(@PathVariable Long id, @RequestParam("status") String status) {
        log.info("修改预约状态，预约ID：{}, 预约状态 {}", id, status);
        int row = appointmentService.updateAppointmentStatus(id, status);
        return Result.success(row);
    }
} 