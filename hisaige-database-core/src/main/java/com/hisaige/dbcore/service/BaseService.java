package com.hisaige.dbcore.service;

import com.hisaige.dbcore.entity.dto.PageReq;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.mapperhelper.EntityHelper;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

/**
 * 基础service
 * @author chenyj
 * @param <T>
 */
public interface BaseService<T> {

    /**
     * 获取实体对象
     * @param id
     * @return
     */
    T get(Serializable id);

    /**
     * 通用请求接口
     * @param paramsMap 参数对象
     * @return 如果带了分页参数currentPage和pageSize，则返回分页对象PageRes，否则返回list
     */
    Object getByParams(Map<String, String[]> paramsMap);

    /**
     * 根据实体中不为空的属性查询，sql使用等号拼接
     * @param t
     * @return List<T>
     */
    List<T> get(T t);

    /**
     * 获取所有实体对象
     * @return List<T>
     */
    List<T> getAll();

    /**
     * 根据实体查询,实体继承TimeSearchDTO
     * 根据实体创建的时间范围进行查询，请求参数包含 startTime | endTime
     * @param pageDTO 查询实体
     * @return List<T>
     */
    List<T> getByProvider(PageReq pageDTO, T record);

    List<T> getByProvider(T record);

    /**
     * 保存实体， 主键会回写到实体中，可以从实体中获取主键
     * @param record 保存实体
     * @return String 实体ID
     */
    int save(T record);

    /**
     * 保存实体，不为null的字段
     * @param record 实体信息
     * @return String 实体ID
     */
    int saveSelective(T record);

    /**
     * 保存全部
     * @param records
     * @return
     */
    int saveAll(List<T> records);


    /**
     * 根据主键修改实体的所有属性
     * @param record 待修改的实体对象
     * @return 执行成功数
     */
    int updateByPrimaryKey(T record);


    /**
     * 根据主键修改实体的部分属性，实体为null的不修改
     * @param record 待修改的实体对象
     * @return 执行成功数
     */
    int updateByPrimaryKeySelective(T record);

    /**
     * 删除实体对象
     * @param record 待删除的实体
     * @return boolean 删除成功返回true
     */
    boolean del(T record) throws Exception;

    /**
     * 根据主键删除实体对象
     * @param id
     * @return boolean
     */
    boolean delById(Serializable id);

    /**
     * 判断是否存在
     * @param id id
     * @return boolean
     */
    boolean existsWithPrimaryKey(Serializable id);

    /**
     * 根据id列表获取
     * @param ids id列表
     * @return List<T>
     */
    List<T> getByIds(Iterable<? extends Serializable> ids);

    /**
     * 获取实体类的表名
     * @return String 监听者全类名
     */
    default String getTableName(){
        return EntityHelper.getEntityTable(getEntityClass()).getName();
    }

    /**
     * 获取当前接口的泛型类型
     * ps: 获取父类的泛型类型
     *  ParameterizedType genericSuperclass = (ParameterizedType)this.getClass().getGenericSuperclass();
     *  (Class<T>) genericSuperclass.getActualTypeArguments()[0];
     * @return Class<T> or null 获取失败返回null
     */
    default Class<T> getEntityClass() {
        @SuppressWarnings("unchecked")
        Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        return entityClass;
    }

    /**
     * 通用请求前预处理
     * @param example
     * @param criteria
     * @param paramsMap
     */
    default void handleBeforeGetByParams(Example example, Example.Criteria criteria, Map<String, String[]> paramsMap) {
    }


}
