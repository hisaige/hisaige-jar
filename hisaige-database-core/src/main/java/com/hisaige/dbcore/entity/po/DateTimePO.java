package com.hisaige.dbcore.entity.po;

import com.hisaige.dbcore.entity.dto.TimeSearchDTO;

import javax.persistence.Column;
import java.util.Date;

/**
 * 数据库中记录实体的创建时间以及更新时间，同时提供查询DTO对象
 * @author chenyj
 * 2020/6/21 - 23:29.
 **/
public class DateTimePO extends TimeSearchDTO {

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
