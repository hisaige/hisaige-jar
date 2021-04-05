package com.hisaige.dbcore.service.impl;

import com.hisaige.dbcore.entity.dto.PageReq;
import com.hisaige.dbcore.entity.dto.TimeSearchDTO;
import com.hisaige.dbcore.entity.po.DateTimePO;
import com.hisaige.dbcore.mapper.BaseMapper;
import com.hisaige.dbcore.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 通用service层
 * @author chenyj
 * 2019/9/10 - 18:30
 **/
public class BaseServiceImpl<MAPPER extends BaseMapper<T>, T> implements BaseService<T> {

    private static final Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);

    /**
     * 不能用resource
     */
    @Autowired
    public MAPPER mapper;

    @Override
    public T get(Serializable id)throws Exception {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public List<T> getByExample(Example example) {
        return mapper.selectByExample(example);
    }

    @Override
    public Object getByParams(Map<String, String[]> paramsMap)throws Exception {
        Example example = new Example(getEntityClass());
        Example.Criteria criteria = example.createCriteria();

        //前置处理
        handleBeforeGetByParams(example, criteria, paramsMap);



        return null;
    }

    @Override
    public List<T> get(T t)throws Exception {
        Assert.notNull(t, "msg.provider.null, clazz:" + t.getClass().getSimpleName());
        return mapper.select(t);
    }

    @Override
    public List<T> getAll()throws Exception{
        return mapper.selectAll();
    }

    @Override
    public List<T> getByProvider(PageReq pageDTO, T record)throws Exception {
//        Assert.notNull(record, "msg.provider.null, clazz:" + record.getClass().getSimpleName());
        Example example = new Example(getEntityClass());

        if(!StringUtils.isEmpty(pageDTO.getOrderProperty())){
            //排序
            Example.OrderBy orderBy = example.orderBy(pageDTO.getOrderProperty());
            if("asc".equalsIgnoreCase(pageDTO.getDirection())){
                orderBy.asc();
            } else {
                orderBy.desc();
            }
        }

        Example.Criteria criteria = example.createCriteria();
        //使用不为空的对象属性查询，使用=号
        criteria.andEqualTo(record);

        if(record instanceof TimeSearchDTO){
            //创建时间范围查询
            TimeSearchDTO timeSearchDTO = (TimeSearchDTO)record;
            if(null != timeSearchDTO.getStartTime()){
                criteria.andGreaterThanOrEqualTo("createTime", timeSearchDTO.getStartTime());
            }
            if(null != timeSearchDTO.getEndTime()){
                criteria.andLessThan("createTime", timeSearchDTO.getEndTime());
            }
        }

        return mapper.selectByExample(example);
//        return mapper.select(record);
    }

    @Override
    public List<T> getByProvider(T record)throws Exception {
        Assert.notNull(record, "msg.provider.null, clazz:" + record.getClass().getSimpleName());
        if(record instanceof TimeSearchDTO){
            Example example = new Example(getEntityClass());
            Example.Criteria criteria = example.createCriteria();
            TimeSearchDTO timeSearchDTO = (TimeSearchDTO)record;
            if(null != timeSearchDTO.getStartTime()){
                criteria.andGreaterThanOrEqualTo("createTime", timeSearchDTO.getStartTime());
            }
            if(null != timeSearchDTO.getEndTime()){
                criteria.andLessThan("createTime", timeSearchDTO.getEndTime());
            }
            criteria.andEqualTo(record);
            return mapper.selectByExample(example);
        }
        return mapper.select(record);
    }

    @Override
    public int save(T record)throws Exception{
        assert null != record;
        setDateTime(record);
        int insert = mapper.insert(record);
        logger.debug("save {}:{}, active:{}", record.getClass().getSimpleName(), record, insert);
        return insert;
    }

    @Override
    public int saveSelective(T record) throws Exception {
        assert null != record;
        setDateTime(record);
        int insert = mapper.insertSelective(record);
        logger.debug("save {}:{}, active:{}", record.getClass().getSimpleName(), record, insert);
        return insert;
    }

    @Override
    public int saveAll(List<T> records)throws Exception {
        if(!CollectionUtils.isEmpty(records)){

            if(DateTimePO.class.isAssignableFrom(getEntityClass())){
                Date now = new Date();
                for (T t : records){
                    DateTimePO dateTimePO = (DateTimePO)t;
                    dateTimePO.setCreateTime(now);
                    dateTimePO.setUpdateTime(now);
                    handlerForEach(t);
                }
            }
            int inserts = mapper.insertList(records);
            logger.debug("saveAll {}:{}, active:{}", records.get(0).getClass().getSimpleName(), records, inserts);
            return inserts;
        }
        return 0;
    }

    @Override
    public int updateByPrimaryKey(T record)throws Exception {
        int update = mapper.updateByPrimaryKey(record);
        logger.info("updateByPrimaryKey {}:{}, active:{}", record.getClass().getSimpleName(), record, update);
        return update;
    }

    @Override
    public int updateByPrimaryKeySelective(T record)throws Exception {
        int update = mapper.updateByPrimaryKeySelective(record);
        logger.info("updateByPrimaryKeySelective {}:{}, active:{}", record.getClass().getSimpleName(), record, update);
        return update;
    }

    @Override
    public boolean del(T record) throws Exception {
        int del = mapper.delete(record);
        logger.info("del entity {}:{}, active:{}", record.getClass().getSimpleName(), record, del);
        return del > 0;
    }

    @Override
    public boolean delById(Serializable id)throws Exception{
        T t = mapper.selectByPrimaryKey(id);
        if (null != t){
            int del = mapper.deleteByPrimaryKey(id);
            logger.info("delById, entity {}:{}, active:{}", t.getClass().getSimpleName(), t, del);
            return del > 0;
        }
        return false;
    }

    @Override
    public boolean existsWithPrimaryKey(Serializable id) {
        return mapper.existsWithPrimaryKey(id);
    }

    @Override
    public List<T> getByIds(Iterable<? extends Serializable> ids)throws Exception {
        if(null == ids || !ids.iterator().hasNext()){
            return new ArrayList<>();
        }
        Example example = new Example(getEntityClass());
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id", ids);
        return mapper.selectByExample(example);
    }

    /**
     * 当涉及到本服务中的遍历操作，对单个实体处理回调暴露方法
     * @param t 实体对象
     */
    protected void handlerForEach(T t){

    }

    /**
     * 为实体设置时间信息
     * @param record 实体
     */
    private void setDateTime(T record) {
        if (DateTimePO.class.isAssignableFrom(getEntityClass())) {
            Date now = new Date();
            DateTimePO dateTimePO = (DateTimePO)record;
            dateTimePO.setCreateTime(now);
            dateTimePO.setUpdateTime(now);
        }
    }
}
