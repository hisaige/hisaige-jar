package com.hisaige.web.core.configuration.mvc;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.ServletModelAttributeMethodProcessor;

import javax.servlet.ServletRequest;
import java.util.Objects;

/**
 * @author chenyj
 * @version 1.0
 * @date 2021/4/5$ - 22:39$
 */
public class EmptyStringToNullResolveProcessor extends ServletModelAttributeMethodProcessor implements ApplicationContextAware {// implements ApplicationContextAware , BeanFactoryPostProcessor {

    ApplicationContext applicationContext;

    public EmptyStringToNullResolveProcessor(boolean annotationNotRequired) {
        super(annotationNotRequired);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    protected void bindRequestParameters(WebDataBinder binder, NativeWebRequest request) {
        EmptyStringToNullDataResolveBinder toNullRequestDataBinderBinder = new EmptyStringToNullDataResolveBinder(binder.getTarget(), binder.getObjectName());
        RequestMappingHandlerAdapter requestMappingHandlerAdapter = applicationContext.getBean(RequestMappingHandlerAdapter.class);
        Objects.requireNonNull(requestMappingHandlerAdapter.getWebBindingInitializer()).initBinder(toNullRequestDataBinderBinder);
        toNullRequestDataBinderBinder.bind(Objects.requireNonNull(request.getNativeRequest(ServletRequest.class)));
    }

}
