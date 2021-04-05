package com.hisaige.web.core.configuration.mvc;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.web.servlet.mvc.method.annotation.ExtendedServletRequestDataBinder;

import javax.servlet.ServletRequest;

/**
 * @author chenyj
 * @version 1.0
 * @date 2021/4/5$ - 22:40$
 */
public class EmptyStringToNullDataResolveBinder extends ExtendedServletRequestDataBinder {
    public EmptyStringToNullDataResolveBinder(Object target, String objectName) {
        super(target, objectName);
    }

    @Override
    protected void addBindValues(MutablePropertyValues mpvs, ServletRequest request) {
        super.addBindValues(mpvs, request);
        for (PropertyValue propertyValue : mpvs.getPropertyValueList()) {
            if("".equals(propertyValue.getValue())) {
                propertyValue.setConvertedValue(null);
            }
        }
    }
}
