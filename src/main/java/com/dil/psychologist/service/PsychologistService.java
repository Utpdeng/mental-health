package com.dil.psychologist.service;

import com.dil.common.exception.BusinessException;
import com.dil.psychologist.dto.PsychologistListResponse;
import com.dil.psychologist.dto.PsychologistDetailResponse;
import com.dil.psychologist.dto.UpdatePsychologistRequest;
import com.dil.psychologist.mapper.PsychologistMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.math.BigDecimal;
import java.util.HashMap;

/**
 * 心理医生服务
 */
@Slf4j
@Service
public class PsychologistService {
    
    @Autowired
    private PsychologistMapper psychologistMapper;
    
    public List<PsychologistListResponse> getPsychologists(String employmentStatus) {
        log.info("获取心理医生列表，参数：employmentStatus={}", employmentStatus);
        return psychologistMapper.getPsychologists(employmentStatus);
    }
    
    public PsychologistDetailResponse getPsychologistDetail(Long psychologistId) {
        log.info("获取心理医生详情，心理医生ID：{}", psychologistId);
        return psychologistMapper.getPsychologistDetail(psychologistId);
    }
    
    /**
     * 更新心理医生信息
     *
     * @param request 更新请求
     */
    @Transactional(rollbackFor = Exception.class)
    public void updatePsychologist(UpdatePsychologistRequest request) {
        log.info("更新心理医生信息，心理医生ID：{}", request.getPsychologistId());
        
        // 检查心理医生是否存在
        PsychologistDetailResponse psychologist = getPsychologistDetail(request.getPsychologistId());
        if (psychologist == null) {
            throw new BusinessException("心理医生不存在");
        }

        if (request.getIsDelete() != null && request.getIsDelete()) {
            // 执行软删除
            int rows = psychologistMapper.deletePsychologist(request.getPsychologistId());
            if (rows == 0) {
                throw new BusinessException("删除心理医生失败");
            }
            log.info("心理医生已删除，ID：{}", request.getPsychologistId());
        } else {
            // 更新信息
            int rows = psychologistMapper.updatePsychologist(request);
            if (rows == 0) {
                throw new BusinessException("更新心理医生信息失败");
            }
            log.info("心理医生信息已更新，ID：{}", request.getPsychologistId());
        }
    }
} 