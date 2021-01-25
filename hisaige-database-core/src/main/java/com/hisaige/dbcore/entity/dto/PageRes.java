package com.hisaige.dbcore.entity.dto;

import com.github.pagehelper.PageInfo;
import com.hisaige.web.core.util.CollectionUtils;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenyj
 * 2020/5/23 - 16:49.
 **/
public class PageRes<T> {
    private Integer pageNum;
    private Integer pageSize;
    private Integer totalPage;
    private Long total;
    private List<T> list;

    /**
     * 将PageHelper分页结果
     */
    public static <T> PageRes<T> restPage(List<T> list) {
        PageRes<T> result = new PageRes<>();
        PageInfo<T> pageInfo = new PageInfo<>(list);
        result.setTotalPage(pageInfo.getPages());
        result.setPageNum(pageInfo.getPageNum());
        result.setPageSize(pageInfo.getPageSize());
        result.setTotal(pageInfo.getTotal());
        result.setList(pageInfo.getList());
        return result;
    }

    /**
     * 根据分页参数进行物理分页
     */
    public static <T> PageRes<T> restPage(List<T> list, int pageNum, int pageSize) {
        PageRes<T> result = new PageRes<>();

        if(CollectionUtils.isEmpty(list)){
            result.setTotalPage(1);
            result.setTotal(0L);
        } else {
            int size = list.size() / pageSize;
            result.setTotalPage(list.size() % pageSize == 0 ? size : size + 1);
        }
        result.setTotal((long) list.size());
        result.setPageNum(pageNum);
        result.setPageSize(pageSize);

        int startIndex = (pageNum - 1) * pageSize;
        int endIndex = pageNum * pageSize;
        if(list.size() < startIndex){
            result.setList(new ArrayList<>());
        } else if(list.size() < endIndex) {
            result.setList(list.subList(startIndex, list.size()));
        }
        return result;
    }

    /**
     * 将SpringData分页结果
     */
    public static <T> PageRes<T> restPage(Page<T> pageInfo) {
        PageRes<T> result = new PageRes<>();
        result.setTotalPage(pageInfo.getTotalPages());
        result.setPageNum(pageInfo.getNumber());
        result.setPageSize(pageInfo.getSize());
        result.setTotal(pageInfo.getTotalElements());
        result.setList(pageInfo.getContent());
        return result;
    }

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

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
