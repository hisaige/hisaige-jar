package com.hisaige.redis.service.impl;

import com.hisaige.redis.service.UserUtilsService;

/**
 * @author chenyj
 * 2020/12/15 - 23:58.
 **/
public class UserUtilsServiceImpl implements UserUtilsService {

    /**
     * 必须自己实现UserUtilsService这个接口，否则使用hisaige当默认用户名
     * @return userName
     */
    @Override
    public String getUserName() {
        return "hisaige";
    }
}
