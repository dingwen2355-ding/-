package com.znv.manage.common.encrypt.impl;

import com.znv.manage.common.encrypt.AESUtil;
import com.znv.manage.common.encrypt.EncryptAlgorithmService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * AES加密算法
 *
 * @author ybw
 */
@Slf4j
@Service
public class AesEncryptAlgorithmServiceImpl implements EncryptAlgorithmService {
    /**
     * 加密操作
     *
     * @param content  待加密内容
     * @param password 加密密码
     * @return 返回Base64转码后的加密数据
     */
    @Override
    public String encrypt(String content, String password) {
        return AESUtil.encrypt(content, password);
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
        return AESUtil.decrypt(content, password);
    }

    /**
     * 功能描述: 一级解密方法（不做解密操作）
     * 创建时间：2019/12/16 19:18
     * 创 建 人：chenhui
     * @param content :
     * @param password :
     * @return java.lang.String
     * @throws
     */
    @Override
    public String outDecrypt(String content, String password) {
        log.info("'AesEncryptAlgorithmServiceImpl' 一级解密方法（不做解密操作）...");
        return content;
    }
}
