package com.hisaige.web.core.util;

import com.hisaige.web.core.exception.RequestNotFoundException;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author chenyj
 * 2020/6/18 - 20:11.
 **/
public class RequestUtils {

    /**
     * 获取客户端IP
     * @return IP
     */
    public static String getReqIp(){
        return IpUtils.getRequestIp();
    }

    /**
     * 获取用户名，注意配合实际项目使用
     * @return String
     */
    public static String getReqUserName(){
        HttpServletRequest request = getRequest();
        if(null == request){
            return null;
        }
        String userName = request.getHeader("userName");
        if(StringUtils.isEmpty(userName)){
            userName = (String) request.getAttribute("userName");
        }
        return userName;
    }

    /**
     * 获取客户端IP
     * @return HttpServletRequest
     */
    public static HttpServletRequest getRequest(){
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if(null != requestAttributes){
            return ((ServletRequestAttributes) requestAttributes).getRequest();
        }
        throw new RequestNotFoundException("cannot get HttpServletRequest");
    }

    /**
     * 获取用户名，注意配合实际项目使用
     * @return String
     */
    public static String getHeadOrAttribute(String param){
        HttpServletRequest request = getRequest();
        if(null == request){
            return null;
        }
        String value = request.getHeader(param);
        if(StringUtils.isEmpty(value)){
            value = (String) request.getAttribute(param);
        }
        return value;
    }
}
