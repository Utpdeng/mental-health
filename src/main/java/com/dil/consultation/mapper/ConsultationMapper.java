package com.dil.consultation.mapper;

import com.dil.consultation.entity.Consultation;
import com.dil.consultation.dto.ConsultationResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 咨询记录Mapper接口
 */
@Mapper
public interface ConsultationMapper {
    /**
     * 创建咨询记录
     */
    int createConsultation(Consultation consultation);

    /**
     * 更新咨询记录
     */
    int updateConsultation(Consultation consultation);

    /**
     * 查询咨询记录列表
     */
    List<ConsultationResponse> getConsultationList(@Param("doctorId") Long doctorId,
                                                 @Param("userId") Long userId,
                                                 @Param("appointmentId") Long appointmentId,
                                                   @Param("form") String form);

    /**
     * 根据ID查询咨询记录详情
     *
     * @param id 咨询记录ID
     * @return 咨询记录详情
     */
    ConsultationResponse getConsultationById(@Param("id") Long id);
} 