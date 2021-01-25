package com.hisaige.dbcore.mapper;

import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.common.Mapper;

/**
 * 这个注解代表 利用我们定义的事务做事务控制<code>TxConfiguration</code>
 * 注意 不要加 @Mapper 注解
 * @author chenyj
 * @date 2019年5月13日
 * @param <T> 实体类
 */
public interface BaseMapper<T> extends Mapper<T>,
        InsertListMapper<T> {

	//String getPkColumnName() throws Exception;
}
