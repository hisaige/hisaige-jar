package com.hisaige.redis.annotation;

import com.hisaige.redis.config.RedisConfiguration;
import com.hisaige.redis.config.RepeatLimitConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author chenyj
 * 2020/6/17 - 17:37.
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EnableAsync
@Import({RedisConfiguration.class, RepeatLimitConfiguration.class})
public @interface EnableRedis {
}
