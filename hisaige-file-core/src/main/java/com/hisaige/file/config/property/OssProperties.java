package com.hisaige.file.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author chenyj
 * 2020/6/25 - 20:12.
 **/
@ConfigurationProperties(prefix = "aliyun.oss.file")
public class OssProperties {

    private String endpoint;

    private String keyId;

    private String keySecret;

    private String bucketName;

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public String getKeySecret() {
        return keySecret;
    }

    public void setKeySecret(String keySecret) {
        this.keySecret = keySecret;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }
}
