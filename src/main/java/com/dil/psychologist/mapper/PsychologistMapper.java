package com.dil.psychologist.mapper;

import com.dil.psychologist.dto.PsychologistListResponse;
import com.dil.psychologist.dto.PsychologistDetailResponse;
import com.dil.psychologist.dto.UpdatePsychologistRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 心理医生Mapper接口
 */
@Mapper
public interface PsychologistMapper {

    /**
     * 获取心理医生列表
     *
     * @return 心理医生列表
     */
    List<PsychologistListResponse> getPsychologists(@Param("employmentStatus") String employmentStatus);

    PsychologistDetailResponse getPsychologistDetail(@Param("psychologistId") Long psychologistId);

    /**
     * 更新心理医生信息
     *
     * @param request 更新请求
     * @return 影响行数
     */
    int updatePsychologist(UpdatePsychologistRequest request);

    /**
     * 软删除心理医生
     *
     * @param psychologistId 心理医生ID
     * @return 影响行数
     */
    int deletePsychologist(@Param("psychologistId") Long psychologistId);
} 