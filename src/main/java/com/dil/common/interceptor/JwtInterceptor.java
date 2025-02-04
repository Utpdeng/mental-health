package com.dil.common.interceptor;

import com.dil.common.context.UserContext;
import com.dil.common.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    // 不需要验证token的路径
    private static final List<String> WHITELIST = Arrays.asList(
            "/api/users/login",
            "/api/users/register",
            "/api/articles/page",
            "/api/articles/*/detail",
            "/error"
    );

    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 检查是否是白名单路径
        String requestPath = request.getRequestURI();
        if (isWhitelistPath(requestPath)) {
            return true;
        }

        // 获取token
        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("please login");
//            return false;
            // 放行所有
            return true;
        }

        try {
            // 验证token
            token = token.substring(7);
            Claims claims = jwtUtil.getClaimsFromToken(token);

            // 将用户信息存储到上下文
            UserContext.UserInfo userInfo = new UserContext.UserInfo();
            userInfo.setUserId(Long.valueOf(claims.get("userId").toString()));
            userInfo.setUsername(claims.get("username").toString());
            userInfo.setRole(claims.get("role").toString());
            UserContext.setUser(userInfo);

            return true;
        } catch (Exception e) {
            log.error("Token验证失败", e);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"status\":\"401\",\"message\":\"token无效或已过期\"}");
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // 清理用户上下文
        UserContext.clear();
    }

    private boolean isWhitelistPath(String requestPath) {
        return WHITELIST.stream().anyMatch(pattern -> pathMatcher.match(pattern, requestPath));
    }
} 