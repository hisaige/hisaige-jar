package com.hisaige.redis.entity.enums;

/**
 * @author chenyj
 * 2020/6/19 - 17:03.
 **/
public enum  LimitType {

    /**
     * 根据IP限流
     */
    IP,

    /**
     * 根据用户限流
     */
    USER,

    /**
     * 根据用户-请求参数限流，注意post的请求体不作为参数限流指标，仅取url上的参数
     */
    ARGS,

    /**
     * 针对接口访问限流，不区分IP或用户
     */
    ALL
}
