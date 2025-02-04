package com.dil.user.dto;

import lombok.Data;

/**
 * 用户注册请求DTO
 *
 * @author dyy
 * @date 2024/01/12
 */
@Data
public class UserRegisterRequest {
    /**
     * 用户名
     */
    private String username;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 密码
     */
    private String password;
} 