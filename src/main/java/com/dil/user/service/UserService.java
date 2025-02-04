package com.dil.user.service;

import com.dil.common.exception.BusinessException;
import com.dil.common.utils.JwtUtil;
import com.dil.user.dto.*;
import com.dil.user.entity.User;
import com.dil.user.mapper.UserMapper;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * 用户服务
 *
 * @author xxx, dyy
 * @date 2024/01/12
 */
@Slf4j
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 用户注册
     *
     * @param request 注册请求
     * @return 注册成功的用户信息
     */
    @Transactional(rollbackFor = Exception.class)
    public User registerUser(UserRegisterRequest request) {
        log.info("开始处理用户注册请求，用户名：{}", request.getUsername());

        // 检查用户名是否存在
        if (userMapper.findByUsername(request.getUsername()) != null) {
            log.warn("用户名已存在：{}", request.getUsername());
            throw new BusinessException("用户名已存在");
        }

        // 检查邮箱是否存在
        if (userMapper.findByEmail(request.getEmail()) != null) {
            log.warn("邮箱已存在：{}", request.getEmail());
            throw new BusinessException("邮箱已存在");
        }

        // 创建新用户
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole("user");
        user.setStatus("active");
        user.setCreated_at(LocalDateTime.now());
        user.setUpdated_at(LocalDateTime.now());

        userMapper.insertUser(user);
        log.info("用户注册成功，用户ID：{}", user.getId());

        return user;
    }

    /**
     * 用户登录
     *
     * @param request 登录请求
     * @return 登录响应
     */
    public UserLoginResponse login(UserLoginRequest request) {
        log.info("用户登录，用户名：{}", request.getUsername());

        // 查询用户
        User user = userMapper.findByUsernameAndPassword(request.getUsername(), request.getPassword());
        if (user == null) {
            log.warn("用户名或密码错误，用户名：{}", request.getUsername());
            throw new BusinessException("用户名或密码错误");
        }

        // 检查用户状态
        if (!"active".equals(user.getStatus())) {
            log.warn("用户状态异常，用户名：{}，状态：{}", request.getUsername(), user.getStatus());
            throw new BusinessException("账号已被禁用");
        }

        // 生成token
        String token = jwtUtil.generateToken(user);

        // 构建响应
        UserLoginResponse response = new UserLoginResponse();
        response.setToken(token);
        response.setUserId(user.getId());
        response.setRole(user.getRole());

        log.info("用户登录成功，用户ID：{}", user.getId());
        return response;
    }

    /**
     * 获取用户信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    @Transactional(readOnly = true)
    public UserInfoResponse getUserInfo(Long userId) {
        log.info("获取用户信息，用户ID：{}", userId);

        UserInfoResponse userInfo = userMapper.getUserInfoById(userId);
        if (userInfo == null) {
            log.warn("用户不存在，用户ID：{}", userId);
            throw new BusinessException("用户不存在");
        }

        return userInfo;
    }

    /**
     * 获取用户列表
     *
     * @param role 用户角色
     * @return 用户列表
     */
    @Transactional(readOnly = true)
    public List<UserInfoResponse> getUsersByRole(String role) {
        log.info("获取用户列表，角色：{}", role);
        List<UserInfoResponse> users = userMapper.findUsersByRole(role);
        if (users.isEmpty()) {
            log.warn("没有找到角色为 {} 的用户", role);
        }
        return users;
    }
}