package com.znv.manage.common.encrypt.impl;

import com.znv.manage.common.encrypt.EncryptAlgorithmService;
import com.znv.manage.common.encrypt.EncryptFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 根据配置文件，生成加密算法
 * @author ybw
 */
@Service
@Slf4j
public class EncryptFactoryImpl implements EncryptFactory {

    @Value("${znvr.encrypt.factory.type:aes}")
    String type;

    private EncryptAlgorithmService encryptAlgorithmService = null;

    /**
     * 返回加密算法实现类
     *
     * @return
     */
    @Override
    public EncryptAlgorithmService getEncryptAlgorithm() {
        if (encryptAlgorithmService != null){
            return encryptAlgorithmService;
        }
        switch (type.toLowerCase()){
            case "normal":
                encryptAlgorithmService = new NormalEncryptAlgorithmServiceImpl();
                break;
            default:
                encryptAlgorithmService = new AesEncryptAlgorithmServiceImpl();
                break;
        }
        return encryptAlgorithmService;
    }
}
