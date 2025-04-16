package com.dil.consultation.service;

import com.dil.consultation.dto.CreateConsultationRequest;
import com.dil.consultation.dto.UpdateConsultationRequest;
import com.dil.consultation.dto.ConsultationResponse;
import com.dil.consultation.entity.Consultation;
import com.dil.consultation.mapper.ConsultationMapper;
import com.dil.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 咨询记录服务
 */
@Slf4j
@Service
public class ConsultationService {
    
    @Autowired
    private ConsultationMapper consultationMapper;

    /**
     * 创建咨询记录
     */
    @Transactional(rollbackFor = Exception.class)
    public Long createConsultation(CreateConsultationRequest request) {
        log.info("创建咨询记录，预约ID：{}", request.getAppointmentId());
        
        Consultation consultation = new Consultation();
        consultation.setAppointmentId(request.getAppointmentId());
        consultation.setDoctorId(request.getDoctorId());
        consultation.setUserId(request.getUserId());
        
        int rows = consultationMapper.createConsultation(consultation);
        if (rows == 0) {
            throw new BusinessException("创建咨询记录失败");
        }
        
        return consultation.getId();
    }

    /**
     * 更新咨询记录
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateConsultation(UpdateConsultationRequest request) {
        log.info("更新咨询记录，预约ID：{}", request.getId());
        
        Consultation consultation = new Consultation();
        consultation.setId(request.getId());
        consultation.setFeedback(request.getFeedback());
        
        int rows = consultationMapper.updateConsultation(consultation);
        if (rows == 0) {
            throw new BusinessException("更新咨询记录失败");
        }
    }

    /**
     * 查询咨询记录列表
     */
    public List<ConsultationResponse> getConsultationList(Long doctorId, Long userId, Long appointmentId, String form) {
        return consultationMapper.getConsultationList(doctorId, userId, appointmentId, form);
    }

    /**
     * 根据ID查询咨询记录详情
     *
     * @param id 咨询记录ID
     * @return 咨询记录详情
     */
    public ConsultationResponse getConsultationById(Long id) {
        log.info("查询咨询记录详情，ID：{}", id);
        ConsultationResponse consultation = consultationMapper.getConsultationById(id);
        if (consultation == null) {
            throw new BusinessException("咨询记录不存在");
        }
        return consultation;
    }
} 