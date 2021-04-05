package com.hisaige.web.core.entity.domain;

import com.hisaige.web.core.entity.enums.ReturnCodeEnum;
import com.hisaige.web.core.exception.InvalidException;
import com.hisaige.web.core.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.Optional;

/**
 * @author chenyj
 * 2019/9/21 - 17:31.
 **/
public class AjaxExceptionRes extends AjaxMessageRes<Object> {

    private static final Logger logger = LoggerFactory.getLogger(AjaxExceptionRes.class);

    public AjaxExceptionRes(ReturnCodeEnum returnCodeEnum) {
        this.setCode(returnCodeEnum.getCode());
        this.setDesc(returnCodeEnum.getDesc_CN());
    }

    public AjaxExceptionRes(BindingResult result) {
        List<ObjectError> list = result.getAllErrors();
        StringBuilder errorMsg = new StringBuilder();
        for (ObjectError error : list) {
            FieldError fieldError = (FieldError) error;
            errorMsg.append(",").append(fieldError.getField()).append(":").append(error.getDefaultMessage());
        }
        this.setDesc(errorMsg.toString().substring(1));
        logger.error(this.getDesc());
    }

    public AjaxExceptionRes(Exception e) {
        logger.error(e.getMessage(), e);
        ReturnCodeEnum returnCodeEnum;
        String desc = null;
        //这里主要处理自定义异常，JDK定义的异常处理详见InvalidExceptionHandler
        if(e instanceof InvalidException) {
            returnCodeEnum = ((InvalidException) e).getReturnCodeEnum();
            desc = ((InvalidException) e).getDesc();
        } else if(e.getCause() instanceof InvalidException) {
            returnCodeEnum = ((InvalidException) e.getCause()).getReturnCodeEnum();
            desc = ((InvalidException) e.getCause()).getDesc();
        } else {
            returnCodeEnum = ReturnCodeEnum.SERVER_EXCEPTION;
            desc = ": " +e.getLocalizedMessage();
        }
        this.setCode(returnCodeEnum.getCode());
        //这里可以根据国际化 Locale 来做国际化处理，暂时先返回中文
        this.setDesc(StringUtils.isEmpty(desc) ?returnCodeEnum.getDesc_CN():returnCodeEnum.getDesc_CN() + ":" + desc);
    }

}
