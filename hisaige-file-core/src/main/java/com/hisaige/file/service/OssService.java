package com.hisaige.file.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * @author chenyj
 * 2020/6/25 - 20:58.
 **/
public interface OssService {
    String uploadFile(MultipartFile file) throws IOException, NoSuchAlgorithmException;

    String uploadFile(String filePath, MultipartFile file) throws IOException, NoSuchAlgorithmException;

    void deleteFile(String fileName);

    boolean isFileExist(String fileName);
}
