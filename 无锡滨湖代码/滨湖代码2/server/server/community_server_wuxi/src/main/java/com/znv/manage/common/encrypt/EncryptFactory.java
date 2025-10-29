package com.znv.manage.common.encrypt;

/**
 * 加密算法工厂类
 * @author ybw
 */
public interface EncryptFactory {
    /**
     * 返回加密算法实现类
     * @return
     */
    EncryptAlgorithmService getEncryptAlgorithm();
}
