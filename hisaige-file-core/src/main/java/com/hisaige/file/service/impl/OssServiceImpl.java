package com.hisaige.file.service.impl;

import com.aliyun.oss.OSS;
import com.hisaige.file.config.property.OssProperties;
import com.hisaige.file.service.OssService;
import com.hisaige.web.core.entity.constants.CoreConstants;
import com.hisaige.web.core.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.UUID;

/**
 * @author chenyj
 * 2020/6/25 - 20:58.
 **/
public class OssServiceImpl implements OssService {

    @Autowired
    private OSS ossClient;

    @Autowired
    private OssProperties ossProperties;

    @Override
    public String uploadFile(MultipartFile file) throws IOException, NoSuchAlgorithmException {
        //添加UUID避免重复照片覆盖
        String fileName = UUID.randomUUID().toString() + file.getOriginalFilename();
        String date = DateUtils.formatDate(new Date(), "yyyy/MM/dd/");
        fileName = date + fileName;
        return upload(file, fileName);
    }

    @Override
    public String uploadFile(String filePath, MultipartFile file) throws IOException, NoSuchAlgorithmException {
        //filePath当作文件夹路径，文件添加uuid名避免重复覆盖
        String fileName = UUID.randomUUID().toString() + file.getOriginalFilename();
        fileName = filePath + "/" + fileName;
        return upload(file, fileName);
    }

    @Override
    public void deleteFile(String fileName) {
        ossClient.deleteObject(ossProperties.getBucketName(), fileName);
    }

    @Override
    public boolean isFileExist(String fileName) {
        return ossClient.doesObjectExist(ossProperties.getBucketName(), fileName);
    }

    private String upload(MultipartFile file, String fileName) throws IOException {

        ossClient.putObject(ossProperties.getBucketName(), fileName, file.getInputStream());
        StringBuilder sb = new StringBuilder("https://").append(ossProperties.getBucketName()).append(".").append(ossProperties.getEndpoint()).append("/");
//        return sb.append(URLEncoder.encode(fileName, CoreConstants.UTF_8)).toString();
        return sb.append(fileName).toString();
    }
}
