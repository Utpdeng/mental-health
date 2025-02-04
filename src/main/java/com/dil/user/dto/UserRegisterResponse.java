package com.dil.user.dto;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 用户注册响应DTO
 *
 * @author dyy
 * @date 2024/01/12
 */
@Data
public class UserRegisterResponse {
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 创建时间
     */
    private LocalDateTime created_at;
    
    /**
     * 更新时间
     */
    private LocalDateTime updated_at;
} 