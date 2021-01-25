package com.hisaige.file.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.hisaige.file.config.property.OssProperties;
import com.hisaige.file.service.OssService;
import com.hisaige.file.service.impl.OssServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * 阿里云文件上传自动装配
 * @author chenyj
 * 2020/6/25 - 20:09.
 **/
@EnableConfigurationProperties({OssProperties.class})
public class OssAutoConfiguration {

    @Autowired
    private OssProperties ossProperties;

    @Bean
    public OSS ossClient(){
        return new OSSClientBuilder().build(ossProperties.getEndpoint(), ossProperties.getKeyId(), ossProperties.getKeySecret());
    }

    @Bean
    public OssService ossService(){
        return new OssServiceImpl();
    }
}
