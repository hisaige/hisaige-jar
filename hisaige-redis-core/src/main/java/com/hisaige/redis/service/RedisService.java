package com.hisaige.redis.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author chenyj
 * 2020/6/17 - 16:23.
 **/
public interface RedisService<T> {

    /**
     * 保存属性
     * @param key key值
     * @param value 保存值
     * @param time 过期时间，单位秒
     */
    void set(String key, T value, long time);

    /**
     * 保存属性
     */
    void set(String key, T value);

    /**
     * 获取属性
     */
    T get(String key);

    /**
     * 删除属性
     */
    Boolean del(String key);

    /**
     * 批量删除属性
     */
    Long del(List<String> keys);

    /**
     * 设置过期时间
     */
    Boolean expire(String key, long time, TimeUnit timeUnit);

    /**
     * 获取过期时间
     */
    Long getExpire(String key);

    /**
     * 获得缓存的基本对象列表
     *
     * @param pattern 字符串前缀
     * @return 对象列表
     */
    Collection<String> keys(final String pattern);

    /**
     * 判断是否有该属性
     */
    Boolean hasKey(String key);

    /**
     * 按delta递增
     */
    Long incr(String key, long delta);

    /**
     * 按delta递减
     */
    Long decr(String key, long delta);

    /**
     * 获取Hash结构中的属性
     */
    T hGet(String key, String hashKey);

    /**
     * 向Hash结构中放入一个属性
     */
    Boolean hSet(String key, String hashKey, T value, long time, TimeUnit timeUnit);

    /**
     * 向Hash结构中放入一个属性
     */
    void hSet(String key, String hashKey, T value);

    /**
     * 直接获取整个Hash结构
     */
    Map<Object, Object> hGetAll(String key);

    /**
     * 直接设置整个Hash结构
     */
    Boolean hSetAll(String key, Map<String, Object> map, long time, TimeUnit timeUnit);

    /**
     * 直接设置整个Hash结构
     */
    void hSetAll(String key, Map<String, Object> map);

    /**
     * 删除Hash结构中的属性
     */
    void hDel(String key, Object... hashKey);

    /**
     * 判断Hash结构中是否有该属性
     */
    Boolean hHasKey(String key, String hashKey);

    /**
     * Hash结构中属性递增
     */
    Long hIncr(String key, String hashKey, Long delta);

    /**
     * Hash结构中属性递减
     */
    Long hDecr(String key, String hashKey, Long delta);

    /**
     * 获取Set结构
     */
    Set<T> sMembers(String key);

    /**
     * 向Set结构中添加属性
     */
    Long sAdd(String key, T... values);

    /**
     * 向Set结构中添加属性
     */
    Long sAddWithExpire(String key, long time, TimeUnit timeUnit, T... values);

    /**
     * 是否为Set中的属性
     */
    Boolean sIsMember(String key, T value);

    /**
     * 获取Set结构的长度
     */
    Long sSize(String key);

    /**
     * 删除Set结构中的属性
     */
    Long sRemove(String key, Object... values);

    /**
     * 获取List结构中的属性
     */
    List<T> lRange(String key, long start, long end);

    /**
     * 获取List结构的长度
     */
    Long lSize(String key);

    /**
     * 根据索引获取List中的属性
     */
    T lIndex(String key, long index);

    /**
     * 向List结构中添加属性
     */
    Long lPush(String key, T value);

    /**
     * 向List结构中添加属性
     */
    Long lPushWithExpire(String key, T value, long time, TimeUnit timeUnit);

    /**
     * 向List结构中批量添加属性
     * @param key key
     * @param values 值可选多个
     * @return Long
     */
    Long lPushAll(String key, T... values);

    /**
     * 向List结构中批量添加属性
     */
    Long lPushAllWithExpire(String key, Long time, TimeUnit timeUnit, T... values);

    /**
     * 从List结构中移除属性
     */
    Long lRemove(String key, long count, T value);
}
