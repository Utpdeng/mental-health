package com.dil.appointment.mapper;

import com.dil.appointment.entity.Appointment;
import com.dil.appointment.dto.AppointmentResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 预约Mapper接口
 */
@Mapper
public interface AppointmentMapper {
    /**
     * 创建预约
     */
    int createAppointment(Appointment appointment);

    /**
     * 查询预约列表
     */
    List<AppointmentResponse> getAppointmentList(@Param("userId") Long userId, 
                                               @Param("doctorId") Long doctorId,
                                               @Param("status") String status);

    /**
     * 查询预约详情
     */
    AppointmentResponse getAppointmentDetail(@Param("id") Long id);

    int updateAppointmentStatus(@Param("id") Long id, @Param("status") String status);
}