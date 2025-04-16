package com.dil.user.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class User {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String role;
    private String status;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
} 