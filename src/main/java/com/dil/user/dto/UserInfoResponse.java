package com.dil.user.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 用户信息响应DTO
 *
 * @author dyy
 * @date 2024/01/14
 */
@Data
public class UserInfoResponse {
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 用户名
     */
    private String username;
    
    /**
     * 邮箱
     */
    private String email;
    
    /**
     * 角色
     */
    private String role;
    
    /**
     * 头像地址
     */
    private String avatarUrl;
    
    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    // 新增字段
    /**
     * 昵称
     */
    private String nickname;
    
    /**
     * 用户简介
     */
    private String bio;
    
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
     * 用户偏好设置（JSON格式）
     */
    private String preferences;

    /**
     * 用户状态
     */
    private String status;

}