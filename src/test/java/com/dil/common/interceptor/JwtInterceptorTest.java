package com.dil.common.interceptor;

import com.dil.common.utils.JwtUtil;
import com.dil.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JwtInterceptorTest {

    @Autowired
    private JwtInterceptor jwtInterceptor;

    @Autowired
    private JwtUtil jwtUtil;

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @BeforeEach
    void setUp() {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
    }

    @Test
    void testWhitelistPath() throws Exception {
        request.setRequestURI("/api/users/login");
        assertTrue(jwtInterceptor.preHandle(request, response, null));
    }

    @Test
    void testNoToken() throws Exception {
        request.setRequestURI("/api/assessments/list");
        assertFalse(jwtInterceptor.preHandle(request, response, null));
        assertEquals(401, response.getStatus());
    }

    @Test
    void testValidToken() throws Exception {
        // 创建测试用户
        User user = new User();
        user.setId(1L);
        user.setUsername("test");
        user.setRole("USER");

        // 生成token
        String token = jwtUtil.generateToken(user);
        request.setRequestURI("/api/assessments/list");
        request.addHeader("Authorization", "Bearer " + token);

        assertTrue(jwtInterceptor.preHandle(request, response, null));
    }

    @Test
    void testInvalidToken() throws Exception {
        request.setRequestURI("/api/assessments/list");
        request.addHeader("Authorization", "Bearer invalid.token.here");

        assertFalse(jwtInterceptor.preHandle(request, response, null));
        assertEquals(401, response.getStatus());
    }
} 