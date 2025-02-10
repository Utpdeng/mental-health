package com.dil.user.dto;

import lombok.Data;

/**
 * 用户登录请求DTO
 *
 * @author dyy
 * @date 2024/01/14
 */
@Data
public class UserLoginRequest {
    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;
} 