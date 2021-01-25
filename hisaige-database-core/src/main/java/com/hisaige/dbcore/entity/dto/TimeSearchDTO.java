package com.hisaige.dbcore.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Transient;
import java.util.Date;

/**
 * @author chenyj
 * 2020/6/21 - 15:22.
 **/
public class TimeSearchDTO {

    @Transient
    private Date startTime;

    @Transient
    private Date endTime;

    @JsonIgnore
    public Date getStartTime() {
        return startTime;
    }

    @JsonProperty
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @JsonIgnore
    public Date getEndTime() {
        return endTime;
    }

    @JsonProperty
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
