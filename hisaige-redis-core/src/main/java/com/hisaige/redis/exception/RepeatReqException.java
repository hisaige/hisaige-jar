package com.hisaige.redis.exception;


import com.hisaige.web.core.entity.enums.ReturnCodeEnum;

/**
 * @author chenyj
 * 2020/6/18 - 17:59.
 **/
public class RepeatReqException extends RuntimeException {
    private ReturnCodeEnum returnCodeEnum;
    public RepeatReqException(ReturnCodeEnum returnCodeEnum){
        super(returnCodeEnum.getDesc_EN());
        this.returnCodeEnum = returnCodeEnum;
    }

    public ReturnCodeEnum getReturnCodeEnum() {
        return returnCodeEnum;
    }
}
