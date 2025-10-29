package cn.wxgis.jc.synch.video.vo;

import lombok.Data;

@Data
public class AuthorizeVo {

    /**
     * 域信息，加密过程使用
     */
    private String realm;

    /**
     * 随机密钥种子
     */
    private String randomKey;

    /**
     * 加密算法
     */
    private String encryptType;

    /**
     * 使用哪种加密流程。没有该字段，按通用加密流程 通 计算，"simple"表示按简易加密流程计算
     */
    private String method;

}
