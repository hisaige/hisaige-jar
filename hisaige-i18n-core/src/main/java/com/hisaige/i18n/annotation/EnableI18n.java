package com.hisaige.i18n.annotation;

import com.hisaige.i18n.config.LocaleConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Import;

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
@Import({LocaleConfiguration.class})
public @interface EnableI18n {


}
