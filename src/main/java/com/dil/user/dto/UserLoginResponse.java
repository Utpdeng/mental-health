package com.dil.user.dto;

import lombok.Data;

/**
 * 用户登录响应DTO
 *
 * @author dyy
 * @date 2024/01/14
 */
@Data
public class UserLoginResponse {
    /**
     * JWT token
     */
    private String token;
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 用户角色
     */
    private String role;
} 