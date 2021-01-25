package com.hisaige.web.core.util;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author chenyj
 * 2019/11/20 - 10:33.
 **/
public class HttpRequestUtils {

    public static HttpServletRequest getRequest(){
        HttpServletRequest request = null;
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        if(ra instanceof ServletRequestAttributes) {
            ServletRequestAttributes sra = (ServletRequestAttributes)ra;
            request = sra.getRequest();
        }
        return request;
    }

}
