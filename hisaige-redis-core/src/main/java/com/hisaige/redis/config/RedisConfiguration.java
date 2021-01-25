package com.hisaige.redis.config;

import com.hisaige.redis.service.RedisService;
import com.hisaige.redis.service.UserUtilsService;
import com.hisaige.redis.service.impl.RedisServiceImpl;
import com.hisaige.redis.service.impl.UserUtilsServiceImpl;
import com.hisaige.redis.util.RedisContext;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.GenericToStringSerializer;

/**
 * @author chenyj
 * 2019/12/30 - 0:51.
 **/
public class RedisConfiguration {

    private RedisConnectionFactory redisConnectionFactory;

    public RedisConfiguration(ObjectProvider<RedisConnectionFactory> redisConnectionFactory, ObjectProvider<GenericJackson2JsonRedisSerializer> genericJackson2JsonRedisSerializer){
        this.redisConnectionFactory = redisConnectionFactory.getIfAvailable();
    }

    /**
     * 重写Redis序列化方式，使用Json方式:
     * 当我们的数据存储到Redis的时候，我们的键（key）和值（value）都是通过Spring提供的Serializer序列化到数据库的。RedisTemplate默认使用的是JdkSerializationRedisSerializer，StringRedisTemplate默认使用的是StringRedisSerializer。
     * Spring Data JPA为我们提供了下面的Serializer：
     * GenericToStringSerializer、Jackson2JsonRedisSerializer、JacksonJsonRedisSerializer、JdkSerializationRedisSerializer、OxmSerializer、StringRedisSerializer。
     * 在此我们将自己配置RedisTemplate并定义Serializer。
     *
     * @return RedisTemplate
     */
    @Bean
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public RedisTemplate redisTemplate() {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory);


        redisTemplate.setKeySerializer(stringRedisSerializer());
        redisTemplate.setHashKeySerializer(stringRedisSerializer());
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer());
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer());

        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean
    public GenericJackson2JsonRedisSerializer jackson2JsonRedisSerializer(){
        return new GenericJackson2JsonRedisSerializer();
    }

    @Bean
    public GenericToStringSerializer<String> stringRedisSerializer(){
        return new GenericToStringSerializer<>(String.class);
    }

    @Bean
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public RedisService redisServiceImpl(RedisTemplate redisTemplate){
        return new RedisServiceImpl(redisTemplate);
    }

    @Bean
    @ConditionalOnMissingBean
    public UserUtilsService userUtilsService(){
        return new UserUtilsServiceImpl();
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisContext redisContext(){
        return new RedisContext(redisTemplate());
    }
}
