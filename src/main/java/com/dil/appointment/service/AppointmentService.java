package com.dil.appointment.service;

import com.dil.appointment.dto.CreateAppointmentRequest;
import com.dil.appointment.dto.AppointmentResponse;
import com.dil.appointment.entity.Appointment;
import com.dil.appointment.mapper.AppointmentMapper;
import com.dil.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 预约服务
 */
@Slf4j
@Service
public class AppointmentService {
    
    @Autowired
    private AppointmentMapper appointmentMapper;

    /**
     * 创建预约
     */
    @Transactional(rollbackFor = Exception.class)
    public Long createAppointment(Long userId, CreateAppointmentRequest request) {
        log.info("创建预约，用户ID：{}，医生ID：{}", userId, request.getDoctorId());
        
        Appointment appointment = new Appointment();
        appointment.setUserId(userId);
        appointment.setDoctorId(request.getDoctorId());
        appointment.setScheduleTime(request.getScheduleTime());
        appointment.setForm(request.getForm());
        appointment.setContent(request.getContent());
        appointment.setTime(request.getTime());
        
        int rows = appointmentMapper.createAppointment(appointment);
        if (rows == 0) {
            throw new BusinessException("创建预约失败");
        }
        
        return appointment.getId();
    }

    /**
     * 查询预约列表
     */
    public List<AppointmentResponse> getAppointmentList(Long userId, Long doctorId, String status) {
        log.info("查询预约列表，用户ID：{}，医生ID：{}，状态：{}", userId, doctorId, status);
        return appointmentMapper.getAppointmentList(userId, doctorId, status);
    }

    /**
     * 查询预约详情
     */
    public AppointmentResponse getAppointmentDetail(Long id) {
        log.info("查询预约详情，预约ID：{}", id);
        AppointmentResponse appointment = appointmentMapper.getAppointmentDetail(id);
        if (appointment == null) {
            throw new BusinessException("预约不存在");
        }
        return appointment;
    }

    public int updateAppointmentStatus(Long id, String status) {
        log.info("修改预约状态：{}", id);
        return appointmentMapper.updateAppointmentStatus(id, status);
    }
}