package com.znv.manage.common.encrypt;

/**
 * 加密算法接口
 *
 * @author Administrator
 */
public interface EncryptAlgorithmService {
    /**
     * 加密操作
     *
     * @param content  待加密内容
     * @param password 加密密码
     * @return 返回Base64转码后的加密数据
     */
    String encrypt(String content, String password);


    /**
     * * 解密操作
     *
     * @param content
     * @param password
     * @return
     */
    String decrypt(String content, String password);

    /**
     * * 一级解密操作
     *
     * @param content
     * @param password
     * @return
     */
    String outDecrypt(String content, String password);
}
