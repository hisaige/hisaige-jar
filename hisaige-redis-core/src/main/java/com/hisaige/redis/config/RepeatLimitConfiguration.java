package com.hisaige.redis.config;

import com.hisaige.redis.aspect.RepeatLimitAspect;
import com.hisaige.redis.service.UserUtilsService;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

/**
 * @author chenyj
 * 2020/6/18 - 16:58.
 **/
public class RepeatLimitConfiguration {

    private RedisTemplate<String, String> redisTemplate;
    private UserUtilsService userUtilsService;

    public RepeatLimitConfiguration(ObjectProvider<RedisTemplate<String, String>> redisTemplate, ObjectProvider<UserUtilsService> userUtilsService){
        this.redisTemplate = redisTemplate.getIfAvailable();
        this.userUtilsService = userUtilsService.getIfAvailable();
    }

    @Bean
    public RepeatLimitAspect repeatLimitAspect(){
        return new RepeatLimitAspect(redisTemplate, limitRedisScript(), userUtilsService);
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisScript<Long> limitRedisScript() {
        DefaultRedisScript redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("redis/limit.lua")));
        redisScript.setResultType(Long.class);
        return redisScript;
    }
}
