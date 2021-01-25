package com.hisaige.web.core.util;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author chenyj
 * 2019/10/24 - 13:14.
 **/
public class FileUtils extends org.apache.commons.io.FileUtils {

    /**
     * 这里linux下或者打jar包运行时可能会有问题
     * 最好别用
     */
    private static final String RESOURCE_PATH = FileUtils.class.getResource("/").getPath().substring(1);

    /**
     * 获取resource资源路径
     * @return resource资源路径
     * @deprecated 生产环境可能会有问题
     */
    public static String getResourcePath(){
        return RESOURCE_PATH;
    }

    /**
     * 获取文件md5，BigInteger类提供的方法进行32进制的转换
     * @param bytes 文件字节数组
     * @return 32为md5值
     * @throws NoSuchAlgorithmException 异常抛出
     */
    public static String md5(byte[] bytes) throws NoSuchAlgorithmException {
        byte[] code = MessageDigest.getInstance("md5").digest(bytes);
        BigInteger bi = new BigInteger(code);
        return bi.abs().toString(32).toUpperCase();
    }
}
