package cn.wxgis.jc.synch.wechat.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WechatAccessTokenVo {

    @ApiModelProperty(value = "出错返回码，为0表示成功，非0表示调用失败")
    private Integer errcode;

    @ApiModelProperty(value = "返回码提示语")
    private String errmsg;

    @ApiModelProperty(value = "获取到的凭证，最长为512字节")
    private String access_token;

    @ApiModelProperty(value = "凭证的有效时间（秒）")
    private Long expires_in;
}
