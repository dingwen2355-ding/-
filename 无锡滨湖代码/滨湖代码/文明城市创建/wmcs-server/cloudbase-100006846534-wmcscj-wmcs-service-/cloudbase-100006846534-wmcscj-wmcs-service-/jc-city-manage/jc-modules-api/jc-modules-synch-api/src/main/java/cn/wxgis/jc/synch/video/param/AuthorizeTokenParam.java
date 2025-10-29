package cn.wxgis.jc.synch.video.param;

import lombok.Data;

@Data
public class AuthorizeTokenParam {

    /**
     * 必填。用户名,最长32字节
     */
    private String userName;

    /**
     * 必填。客户端类型。固定填 winpc
     */
    private String clientType = "winpc";

    /**
     * 必填。根据签名计算方法得到的签名值。
     */
    private String signature;

    /**
     * 必填。随机密钥种子。
     */
    private String randomKey;

    /**
     * 必填。加密算法。
     */
    private String encryptType;

    /**
     * 选填。客户端的ip地址，用于日志审计。
     */
    private String ipAddress;


}
