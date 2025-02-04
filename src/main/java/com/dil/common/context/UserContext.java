package com.dil.common.context;

import lombok.Data;

@Data
public class UserContext {

    private static final ThreadLocal<UserInfo> userHolder = new ThreadLocal<>();

    @Data
    public static class UserInfo {
        private Long userId;
        private String username;
        private String role;
    }

    public static void setUser(UserInfo userInfo) {
        userHolder.set(userInfo);
    }

    public static UserInfo getUser() {
        return userHolder.get();
    }

    public static void clear() {
        userHolder.remove();
    }
} 