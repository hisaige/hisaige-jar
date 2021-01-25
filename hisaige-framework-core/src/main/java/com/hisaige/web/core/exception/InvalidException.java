package com.hisaige.web.core.exception;

import com.hisaige.web.core.entity.enums.ReturnCodeEnum;
import org.springframework.util.StringUtils;

/**
 * @author chenyj
 * 2019/9/21 - 17:19.
 **/
public class InvalidException extends Exception {

    private ReturnCodeEnum returnCodeEnum;
    private String desc;

    public InvalidException(ReturnCodeEnum returnCodeEnum){
        super(returnCodeEnum.getDesc_EN());
        this.returnCodeEnum = returnCodeEnum;
    }

    //重载构造器，添加额外的错误描述
    public InvalidException(ReturnCodeEnum returnCodeEnum, String desc){
        this(returnCodeEnum);
        if(!StringUtils.isEmpty(desc)){
            this.desc = "(" + desc + ")";
        }
    }

    public ReturnCodeEnum getReturnCodeEnum() {
        return returnCodeEnum;
    }

    public String getDesc() {
        return desc;
    }
}
