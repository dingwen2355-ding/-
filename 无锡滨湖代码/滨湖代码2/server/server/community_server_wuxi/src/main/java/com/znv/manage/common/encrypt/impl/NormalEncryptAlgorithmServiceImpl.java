package com.znv.manage.common.encrypt.impl;

import com.znv.manage.common.encrypt.EncryptAlgorithmService;
import lombok.extern.slf4j.Slf4j;

/**
 * 不加密的实现类
 * @author ybw
 */
@Slf4j
public class NormalEncryptAlgorithmServiceImpl implements EncryptAlgorithmService {
    /**
     * 加密操作
     *
     * @param content  待加密内容
     * @param password 加密密码
     * @return 返回Base64转码后的加密数据
     */
    @Override
    public String encrypt(String content, String password) {
        log.info("NormalEncryptAlgorithmServiceImpl encrypt");
        return content;
    }

    /**
     * * 解密操作
     *
     * @param content
     * @param password
     * @return
     */
    @Override
    public String decrypt(String content, String password) {
        log.info("NormalEncryptAlgorithmServiceImpl decrypt");
        return content;
    }

    /**
     * * 一级解密操作
     *
     * @param content
     * @param password
     * @return
     */
    @Override
    public String outDecrypt(String content, String password) {
        log.info("NormalEncryptAlgorithmServiceImpl outDecrypt");
        return content;
    }
}
