package com.hisaige.redis.annotation;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author chenyj
 * 2020/6/18 - 12:12.
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EnableAutoConfiguration
@EnableRedis
@EnableCaching
@IEnableRedisCache
public @interface EnableRedisCache {
}
