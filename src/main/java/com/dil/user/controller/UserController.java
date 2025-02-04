package com.dil.user.controller;

import com.dil.common.response.Result;
import com.dil.user.dto.*;
import com.dil.user.entity.User;
import com.dil.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户控制器
 *
 * @author dyy
 * @date 2024/01/14
 */
@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Resource
    private UserService userService;

    /**
     * 用户注册接口
     *
     * @param request 注册请求
     * @return 注册响应
     */
    @PostMapping("/register")
    public Result<UserRegisterResponse> register(@RequestBody UserRegisterRequest request) {
        log.info("收到用户注册请求，用户名：{}", request.getUsername());

        User user = userService.registerUser(request);

        UserRegisterResponse response = new UserRegisterResponse();
        response.setUserId(user.getId());
        response.setCreated_at(user.getCreated_at());
        response.setUpdated_at(user.getUpdated_at());

        log.info("用户注册成功，用户ID：{}", user.getId());
        return Result.success(response);
    }

    /**
     * 用户登录接口
     *
     * @param request 登录请求
     * @return 登录响应
     */
    @PostMapping("/login")
    public Result<UserLoginResponse> login(@RequestBody UserLoginRequest request) {
        log.info("收到用户登录请求，用户名：{}", request.getUsername());
        UserLoginResponse response = userService.login(request);
        return Result.success(response);
    }

    /**
     * 获取用户列表接口
     *
     * @param role 用户角色
     * @return 用户列表
     */
    @GetMapping("/list")
    public Result<List<UserInfoResponse>> listUsers(@RequestParam(required = false) String role) {
        log.info("收到获取用户列表请求，角色：{}", role);
        List<UserInfoResponse> users = userService.getUsersByRole(role);
        return Result.success(users);
    }

    @GetMapping("/{userId}")
    public Result<UserInfoResponse> getUserInfo(@PathVariable("userId") Long userId) {
        log.info("收到获取用户信息请求，用户ID：{}", userId);
        UserInfoResponse userInfo = userService.getUserInfo(userId);
        return Result.success(userInfo);
    }

    /**
     * 用户退出登录接口
     */
    @PostMapping("/logout")
    public Result<?> logout() {
        return Result.success(null);
    }
}