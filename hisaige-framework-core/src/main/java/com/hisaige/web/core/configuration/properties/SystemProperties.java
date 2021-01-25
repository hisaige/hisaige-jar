package com.hisaige.web.core.configuration.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author chenyj
 * 2020/12/15 - 0:03.
 **/
@ConfigurationProperties(prefix = "hisaige.system.config")
@Data
public class SystemProperties {

    //http代理配置
    private Boolean enableProxy = false;
    private String proxyHost = "localhost";
    private String proxyPort = "8888";

    //swagger 配置
    private Boolean enableSwagger2 = true;

}