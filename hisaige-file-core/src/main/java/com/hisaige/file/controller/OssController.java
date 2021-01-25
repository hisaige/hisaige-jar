package com.hisaige.file.controller;

import com.hisaige.file.service.OssService;
import com.hisaige.web.core.entity.domain.AjaxMessageRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * @author chenyj
 * 2020/6/25 - 20:58.
 * @deprecated 不启用这个controller，具体上传方法由业务自己实现
 **/
//@RestController
//@RequestMapping("/aliyun/oss")
public class OssController {

    @Autowired
    private OssService ossService;

    @PostMapping("/upload")
    public AjaxMessageRes<String> uploadFile(MultipartFile file) throws IOException, NoSuchAlgorithmException {
        String url = ossService.uploadFile(file);
        return new AjaxMessageRes<>(url);
    }

    @PostMapping("/upload/{filePath}")
    public AjaxMessageRes<String> uploadFile(@PathVariable("filePath") String filePath, MultipartFile file) throws IOException, NoSuchAlgorithmException {
        String url = ossService.uploadFile(filePath, file);
        return new AjaxMessageRes<>(url);
    }
}
