package cn.wxgis.jc.synch.wechat.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 政务微信登陆返回用户
 */
@Data
public class WechatLoginInfoVo {

    @ApiModelProperty(value = "返回错误代码")
    private Integer errcode;

    @ApiModelProperty(value = "返回错误信息")
    private String errmsg;

    @ApiModelProperty(value = "用户id")
    private String UserId;

    @ApiModelProperty(value = "手机设备号")
    private String DeviceId;

    @ApiModelProperty(value = "成员票据，最大为512字节。 获取code时，scope为snsapi_userinfo时返回")
    private String user_ticket;

    @ApiModelProperty(value = "user_ticket的有效时间（秒），随user_ticket一起返回")
    private Long expires_in;

    @ApiModelProperty(value = "成员身份信息，2：超级管理员, 4:分级管理员，5：普通成员")
    private Integer usertype;

}
