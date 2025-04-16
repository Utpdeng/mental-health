package com.dil.common.config;

import com.dil.common.annotation.RateLimiterAnnotation;
import com.dil.common.enums.RateLimiterType;
import com.dil.common.utils.IpUtil;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.util.concurrent.RateLimiter;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.platform.commons.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * @author: "xx"
 * @Date: 2025/2/7
 */
@Component
@Aspect
public class RateLimiterAspect {

    private final Logger logger = LoggerFactory.getLogger(RateLimiterAspect.class);

    private final Cache<String, RateLimiter> rateLimiters = CacheBuilder.newBuilder()
            .expireAfterWrite(1, TimeUnit.MINUTES)
            .build();

    private final Cache<String, Long> blacklist = CacheBuilder.newBuilder()
            .expireAfterWrite(24, TimeUnit.HOURS)
            .build();

    @Pointcut("@annotation(com.dil.common.annotation.RateLimiterAnnotation)")
    public void pointCut() {
    }

    @Around("pointCut() && @annotation(rateLimit)")
    public Object doAround(ProceedingJoinPoint joinPoint, RateLimiterAnnotation rateLimit) throws Throwable {
        String key = rateLimit.key();
        logger.info("key: {}", key);
        logger.info("permitsPerSecond: {}", rateLimit.permitsPerSecond());
        logger.info("blacklistCount: {}", rateLimit.blacklistCount());

        // 获取请求IP
        if (RateLimiterType.IP == rateLimit.limitType()) {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            key = key + request.getRemoteAddr();
        }
        logger.info("限流 key: {}", key);

        // 黑名单拦截
        if (rateLimit.blacklistCount() != 0 && blacklist.getIfPresent(key) != null && blacklist.getIfPresent(key) >= rateLimit.blacklistCount()) {
            return fallbackMethodResult(joinPoint, rateLimit.fallbackMethod());
        }

        // 获取限流 -> Guava 缓存1分钟
        RateLimiter rateLimiter = rateLimiters.getIfPresent(key);
        if (rateLimiter == null) {
            rateLimiter = RateLimiter.create(rateLimit.permitsPerSecond());
            rateLimiters.put(key, rateLimiter);
        }

        // 进行限流拦截
        if (!rateLimiter.tryAcquire()) {
            // 超过次数，加入黑名单
            if (rateLimit.blacklistCount() != 0) {
                if (blacklist.getIfPresent(key) == null) {
                    blacklist.put(key, 1L);
                } else {
                    blacklist.put(key, blacklist.getIfPresent(key) + 1L);
                }
            }
            logger.info("限流-超频次拦截：{}", key);
            return fallbackMethodResult(joinPoint, rateLimit.fallbackMethod());
        }

        return joinPoint.proceed();
    }

    private Object fallbackMethodResult(JoinPoint jp, String fallbackMethod) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Signature sig = jp.getSignature();
        MethodSignature methodSignature = (MethodSignature) sig;
        Method method = jp.getTarget().getClass().getMethod(fallbackMethod, methodSignature.getParameterTypes());
        return method.invoke(jp.getThis(), jp.getArgs());
    }

}
