package com.hisaige.dbcore.controller;

import com.github.pagehelper.PageHelper;
import com.hisaige.dbcore.entity.dto.PageReq;
import com.hisaige.dbcore.entity.dto.PageRes;
import com.hisaige.dbcore.service.BaseService;
import com.hisaige.web.core.util.RequestUtils;
import com.hisaige.web.core.valid.AddGroup;
import com.hisaige.web.core.valid.EditGroup;
import com.hisaige.web.core.entity.domain.AjaxMessageRes;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.entity.EntityField;
import tk.mybatis.mapper.mapperhelper.EntityHelper;
import tk.mybatis.mapper.util.Assert;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 包含一些基本的增删查改
 * (groups={AddUser.class}) 新增 (groups={EditUser.class}) 修改
 * @author chenyj
 * 2020/5/19 - 23:58.
 **/
public class BaseController<S extends BaseService<T>, T> {

    /**
     * 不能用resource
     */
    @Autowired
    public S service;

    @GetMapping("/get/{id}")
    @ResponseBody
    @ApiOperation("根据id获取")
    public AjaxMessageRes<T> get(@PathVariable("id") Serializable id){
        Assert.notNull(id, "msg.id.blank");
        return new AjaxMessageRes<>(service.get(id));
    }

    @GetMapping("/getByParams")
    @ResponseBody
    @ApiOperation("根据id获取")
    public AjaxMessageRes<Object> getByParams(){
        LinkedHashMap<String, String[]> reqParamsMap = new LinkedHashMap<>();
        HttpServletRequest request = RequestUtils.getRequest();
        if(null != request){
            Map<String, String[]> parameterMap = request.getParameterMap();
            parameterMap.forEach(reqParamsMap::put);
        }
        return new AjaxMessageRes<>(service.getByParams(reqParamsMap));
    }

    @PostMapping("/create")
    @ResponseBody
    @ApiOperation("根据实体创建,如果创建成功返回的msg为回填的主键值，注意如果多个主键只返回第一个")
    public AjaxMessageRes<Object> create(@RequestBody @Validated({AddGroup.class}) T t) throws InvocationTargetException, IllegalAccessException {
        service.saveSelective(t);
        Set<EntityColumn> pkColumns = EntityHelper.getPKColumns(service.getEntityClass());
        EntityColumn next = pkColumns.iterator().next();
        if(null != next){
            EntityField entityField = pkColumns.iterator().next().getEntityField();
            Object value = entityField.getValue(t);
            return new AjaxMessageRes<>(value);
        }
        return new AjaxMessageRes<>();
    }

    @PostMapping("/creates")
    @ResponseBody
    @ApiOperation("根据实体批量创建")
    public AjaxMessageRes<Integer> create(@RequestBody @Validated({AddGroup.class}) List<T> records){
        return new AjaxMessageRes<>(service.saveAll(records));
    }

    @PutMapping("/update")
    @ResponseBody
    @ApiOperation("根据实体更新")
    public AjaxMessageRes<Integer> updateByPrimaryKeySelective(@RequestBody @Validated({EditGroup.class}) T t){
        return new AjaxMessageRes<>(service.updateByPrimaryKeySelective(t));
    }

    @DeleteMapping("/del/{id}")
    @ResponseBody
    @ApiOperation("根据id删除")
    public AjaxMessageRes<Boolean> delete(@PathVariable("id") Serializable id){
        Assert.notNull(id, "msg.id.blank");
        return new AjaxMessageRes<>(service.delById(id));
    }

    @GetMapping("/search")
    @ResponseBody
    @ApiOperation("根据实体条件检索")
    public AjaxMessageRes<Object> search(PageReq pageDTO, T record){
        if(null != pageDTO.getPageSize() && null != pageDTO.getPageNum()) {
            //分页模式
            PageHelper.startPage(pageDTO.getPageNum(), pageDTO.getPageSize());
            return new AjaxMessageRes<>(PageRes.restPage(service.getByProvider(pageDTO, record)));
        } else {
            return new AjaxMessageRes<>(service.getByProvider(pageDTO, record));
        }
    }

    @PostMapping("/get/ids")
    @ResponseBody
    @ApiOperation("根据ID列表检索")
    public AjaxMessageRes<List<T>> search(@RequestBody List<Long> ids) {
        return new AjaxMessageRes<>(service.getByIds(ids));
    }
}
