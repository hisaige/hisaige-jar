package com.hisaige.dbcore.annotation;

import com.hisaige.dbcore.config.DbConfiguration;
import com.hisaige.dbcore.config.TxConfiguration;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;
import tk.mybatis.spring.annotation.MapperScan;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 启用数据库通用配置
 * @author chenyj
 * 2020/5/16 - 16:59.
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@EnableAutoConfiguration
@MapperScan
@Import({DbConfiguration.class, TxConfiguration.class})
public @interface EnableCoreDatabase {

    @AliasFor(value = "basePackages", annotation = MapperScan.class)
    String[] basePackages() default {};

    @AliasFor(value = "sqlSessionTemplateRef", annotation = MapperScan.class)
    String sqlSessionTemplateRef() default "core_db_sqlSessionTemplate";


    //本意限制mybatis的默认配置,但经过实验发现没有起作用，后续有空再研究一下
    // 当前解决办法：需要在启动main函数处理 @SpringBootApplication(exclude = {MybatisAutoConfiguration.class}) 才最终解决
    @AliasFor(value = "exclude", annotation = EnableAutoConfiguration.class)
    Class<?>[] exclude() default {MybatisAutoConfiguration.class};

}
