package cn.wxgis.jc.synch.wechat.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 微信签名返回对象
 */
@Data
public class WechatSignatureVo {

    @ApiModelProperty(value = "随机数：16个字节")
    private String noncestr;

    private String jsapi_ticket;

    private String timestamp;

    @ApiModelProperty(value = "签名")
    private String signature;

}
