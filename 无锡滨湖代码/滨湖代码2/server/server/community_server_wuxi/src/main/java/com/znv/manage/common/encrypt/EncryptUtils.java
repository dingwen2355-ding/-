package com.znv.manage.common.encrypt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * wr
 * 加解密工具
 */
@Component
public class EncryptUtils {

    @Autowired
    EncryptFactory encryptFactory;

    /**
     * 加解密
     */
    private final Integer ENCRYPT = 1;
    private final Integer DECRYPT = 2;


    /**
     * 加解密业务 list
     * @param list 原始数据
     * @param keysToDecrypt 例：key1,key2,key3
     * @param password 例：123123
     * @param cypherType 1加密 2解密
     * @return
     */
    private void cypherFromList(List<Map<String, Object>> list, String keysToDecrypt, String password, Integer cypherType) {
        if (!StringUtils.isEmpty(keysToDecrypt) && !CollectionUtils.isEmpty(list)) {
            String[] splitKeys = keysToDecrypt.split(",");
            List<String> keysArr = Arrays.asList(splitKeys);
            keysArr.removeAll(Arrays.asList(""));
            if (!keysArr.isEmpty()) {
                list.forEach(map->{
                    keysArr.forEach(key->{
                        String value = String.valueOf(map.getOrDefault(key, ""));
                        if (!StringUtils.isEmpty(value)) {
                            String ret = value;
                            if (ENCRYPT == cypherType) {
                                ret = encryptFactory.getEncryptAlgorithm().encrypt(value, password);
                            } else if (DECRYPT == cypherType) {
                                ret = encryptFactory.getEncryptAlgorithm().decrypt(value, password);
                                map.put(key+"OriginDbVal", value);
                            }
                            map.put(key, ret);
                        }
                    });
                });
            }
        }
    }

    /**
     * 从list中解密相应字段
     * @param list 原始数据
     * @param keysToDecrypt 例：key1,key2,key3
     * @param password 例：123123
     * @return
     */
    public void decryptFromList(List<Map<String, Object>> list, String keysToDecrypt, String password) {
        this.cypherFromList(list, keysToDecrypt, password, DECRYPT);
    }

    /**
     * 从list中加密相应字段
     * @param list 原始数据
     * @param keysToDecrypt 例：key1,key2,key3
     * @param password 例：123123
     * @return
     */
    public void encryptFromList(List<Map<String, Object>> list, String keysToDecrypt, String password) {
        this.cypherFromList(list, keysToDecrypt, password, ENCRYPT);
    }
}
