package com.hisaige.redis.aspect;

import com.hisaige.redis.annotation.RepeatLimit;
import com.hisaige.redis.entity.enums.LimitType;
import com.hisaige.redis.exception.RepeatReqException;
import com.hisaige.redis.service.UserUtilsService;
import com.hisaige.web.core.entity.constants.CoreConstants;
import com.hisaige.web.core.util.RequestUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author chenyj
 * 2020/6/18 - 16:26.
 **/
@Aspect
public class RepeatLimitAspect {

    private static final Logger logger = LoggerFactory.getLogger(RepeatLimitAspect.class);
    private static final String REDIS_LIMIT_KEY_PREFIX = "limit:";

    private RedisTemplate<String, String> redisTemplate;
    private final RedisScript<Long> limitRedisScript;
    private UserUtilsService userUtilsService;

    public RepeatLimitAspect(RedisTemplate<String, String> redisTemplate, RedisScript<Long> limitRedisScript, UserUtilsService userUtilsService) {
        this.redisTemplate = redisTemplate;
        this.limitRedisScript = limitRedisScript;
        this.userUtilsService = userUtilsService;
    }

    // 包含限流注解的controller
    // @annotation(com.hisaige.redis.annotation.RepeatLimit) &&
    @Pointcut("@annotation(com.hisaige.redis.annotation.RepeatLimit)")
    public void repeatLimitPointcut() {
    }

    @Before("repeatLimitPointcut()")
    public void pointcut(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        RepeatLimit repeatLimit = method.getAnnotation(RepeatLimit.class);
        long max = repeatLimit.max();
        long timeout = repeatLimit.timeout();
        TimeUnit timeUnit = repeatLimit.timeUnit();
        String limitKey = limitKey(method, repeatLimit.limitType());

        boolean limited = shouldLimited(limitKey, max, timeout, timeUnit);
        if (limited) {
            throw new RepeatReqException(repeatLimit.returnCodeEnum());
        }
    }

    private boolean shouldLimited(String key, long max, long timeout, TimeUnit timeUnit) {
        // 统一使用单位毫秒
        long ttl = timeUnit.toMillis(timeout);
        // 当前时间毫秒数
        long now = Instant.now().toEpochMilli();
        long expired = now - ttl;

        Long executeTimes = redisTemplate.execute(limitRedisScript, Collections.singletonList(key), now + "", ttl + "", expired + "", max + "");
        if (executeTimes != null) {
            if (executeTimes == 0) {
                logger.debug("request is limited, limit_key:{}, timeout_ttl:{}, req_max:{}", key, ttl, max);
                return true;
            }
        }
        return false;
    }

    /**
     * 获取限流key => limit:ip.clazzName.methodName || limit:userName.clazzName.methodName
     *
     * @param method    Method
     * @param limitType LimitType
     * @return String
     */
    private String limitKey(Method method, LimitType limitType) {

        StringBuilder sb = new StringBuilder(REDIS_LIMIT_KEY_PREFIX);

        switch (limitType) {
            case USER:
                sb.append(userUtilsService.getUserName()).append(CoreConstants.DOT);
                break;
            case ARGS:
                sb.append(userUtilsService.getUserName()).append(CoreConstants.DOT);
                HttpServletRequest request = RequestUtils.getRequest();
                if(null != request){
                    Map<String, String[]> parameterMap = request.getParameterMap();
                    if(!CollectionUtils.isEmpty(parameterMap)){
                        ArrayList<String> list = new ArrayList<>();
                        for (Map.Entry<String, String[]> entry:parameterMap.entrySet()){
                            list.add(entry.getKey());
                            String[] values = entry.getValue();
                            if(!ArrayUtils.isEmpty(values)){
                                list.addAll(Arrays.asList(values));
                            }
                        }
                        sb.append(list.hashCode()).append(CoreConstants.DOT);
                    }
                }
                break;
            case IP:
                sb.append(RequestUtils.getReqIp()).append(CoreConstants.DOT);
                break;
            case ALL:
                break;
        }
        sb.append(method.getDeclaringClass().getName()).append(CoreConstants.DOT).append(method.getName());
        return sb.toString();
    }

}
