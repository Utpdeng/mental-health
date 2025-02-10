package com.dil.common.annotation;


import com.dil.common.enums.RateLimiterType;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface RateLimiterAnnotation {

    /**
     * 限流 key
     */
    String key() default "rate_limit:";

    /**
     * 限制频次（每秒请求次数）
     */
    double permitsPerSecond();

    /**
     * 限流时间,单位秒
     */
    double time() default 60;

    /**
     * 黑名单拦截（多少次限制后加入黑名单）0 不限制
     */
    int blacklistCount() default 0;

    RateLimiterType limitType() default RateLimiterType.IP;

    /**
     * 拦截后的执行方法
     */
    String fallbackMethod();


}
