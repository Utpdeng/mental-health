package com.dil.user.mapper;

import com.dil.user.entity.User;
import com.dil.user.dto.UserInfoResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户Mapper接口
 *
 * @author dyy
 * @date 2024/01/12
 */
@Mapper
public interface UserMapper {
    /**
     * 插入新用户
     *
     * @param user 用户信息
     * @return 影响行数
     */
    int insertUser(User user);

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return 用户信息
     */
    User findByUsername(String username);

    /**
     * 根据邮箱查询用户
     *
     * @param email 邮箱
     * @return 用户信息
     */
    User findByEmail(String email);

    /**
     * 根据用户名和密码查询用户
     *
     * @param username 用户名
     * @param password 密码
     * @return 用户信息
     */
    User findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    /**
     * 根据用户ID查询用户基本信息和详细信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    UserInfoResponse getUserInfoById(@Param("userId") Long userId);

    /**
     * 根据角色查询用户列表
     *
     * @param role 用户角色
     * @return 用户列表
     */
    List<UserInfoResponse> findUsersByRole(@Param("role") String role);
}