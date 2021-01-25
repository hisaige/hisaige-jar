package com.hisaige.web.core.entity.domain;

/**
 * @author chenyj
 * 2019/9/21 - 17:02.
 **/
public class AjaxMessageRes<T> {
    private Integer code = 0;
    //可以使用国际化文件赋值这个字段
    private String desc = "成功";
    private T msg;

    public AjaxMessageRes() {
    }

    public AjaxMessageRes(T t) {
        this.msg = t;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public T getMsg() {
        return msg;
    }

    public void setMsg(T msg) {
        this.msg = msg;
    }
}
