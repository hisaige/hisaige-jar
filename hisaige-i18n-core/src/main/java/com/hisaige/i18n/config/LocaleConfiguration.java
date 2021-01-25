package com.hisaige.i18n.config;

import com.hisaige.i18n.locale.LocaleMessage;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import java.util.Locale;

/**
 * @author chenyj
 * 2020/3/23 - 23:04.
 **/
public class LocaleConfiguration {

    @Bean
    public LocaleResolver localeResolver() {
        CookieLocaleResolver clr = new CookieLocaleResolver();
        //设置默认语言
        clr.setDefaultLocale(Locale.CHINA);
        //设置有效期为一天
        clr.setCookieMaxAge(3600 * 24);
        clr.setCookieName("lang");
        return clr;

    }

    @Bean
    public LocaleMessage localeMessage(){
        return new LocaleMessage();
    }

}
