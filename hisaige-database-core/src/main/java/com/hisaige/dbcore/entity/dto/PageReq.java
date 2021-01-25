package com.hisaige.dbcore.entity.dto;

/**
 * 前端分页查询使用
 * @author chenyj
 * 2020/5/23 - 16:29.
 **/
public class PageReq<T> {

    private Integer pageNum;
    private Integer pageSize;

    private String orderProperty; //排序属性

    private String direction = "desc"; //排序方向 desc| asc

//    private T record; //查询实体，也即条件

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getOrderProperty() {
        return orderProperty;
    }

    public void setOrderProperty(String orderProperty) {
        this.orderProperty = orderProperty;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}
