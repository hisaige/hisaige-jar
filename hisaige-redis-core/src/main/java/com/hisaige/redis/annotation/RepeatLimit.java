package com.hisaige.redis.annotation;

import com.hisaige.redis.entity.enums.LimitType;
import com.hisaige.web.core.entity.enums.ReturnCodeEnum;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * @author chenyj
 * 2020/6/18 - 16:19.
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RepeatLimit {

    long DEFAULT_REQUEST = 10;

    /**
     * max timeout时间内最大请求数
     */
    long max() default DEFAULT_REQUEST;

    /**
     * 超时时长，默认5秒
     */
    long timeout() default 5;

    /**
     * 超时时间单位，默认 秒
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

    LimitType limitType() default LimitType.USER;

    ReturnCodeEnum returnCodeEnum() default ReturnCodeEnum.REQUEST_LIMIT_EXCEPTION;

}
