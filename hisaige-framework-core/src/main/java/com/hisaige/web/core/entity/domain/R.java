package com.hisaige.web.core.entity.domain;

import com.hisaige.web.core.entity.enums.ReturnCodeEnum;

import java.io.Serializable;

/**
 * @author chenyj
 * 2020/12/14 - 23:13.
 **/
public class R implements Serializable {

    public static <T> AjaxMessageRes<T> ok(T t){
        return new AjaxMessageRes<>(t);
    }

    public static AjaxExceptionRes fail(Exception e){
        return new AjaxExceptionRes(e);
    }

    public static AjaxExceptionRes fail(ReturnCodeEnum returnCodeEnum){
        return new AjaxExceptionRes(returnCodeEnum);
    }
}
