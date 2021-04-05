package com.hisaige.web.core.configuration.mvc;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author chenyj
 * @version 1.0
 * @date 2021/4/5$ - 23:13$
 */
public class CoreSpringMvcAutoConfiguration implements ApplicationContextAware, WebMvcConfigurer {

    private ApplicationContext applicationContext;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        EmptyStringToNullResolveProcessor argumentResolver = applicationContext.getBean(EmptyStringToNullResolveProcessor.class);
        if(null != argumentResolver) {
            argumentResolvers.add(0, argumentResolver);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
