package com.dil.user.entity;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 用户详细信息实体
 *
 * @author dyy
 * @date 2024/01/14
 */
@Data
public class UserProfile {
    /**
     * 关联用户ID
     */
    private Long userId;
    
    /**
     * 用户昵称
     */
    private String nickname;
    
    /**
     * 头像地址
     */
    private String avatarUrl;
    
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
     * 用户偏好设置
     */
    private String preferences;
    
    /**
     * 创建时间
     */
    private LocalDateTime created_at;
    
    /**
     * 更新时间
     */
    private LocalDateTime updated_at;
} 