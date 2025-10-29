package cn.wxgis.jc.synch.wechat.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WechatTicketVo {


    @ApiModelProperty(value = "返回错误代码")
    private Integer errcode;

    @ApiModelProperty(value = "返回错误信息")
    private String errmsg;

    @ApiModelProperty(value = "ticket信息")
    private String ticket;

    @ApiModelProperty(value = "user_ticket的有效时间（秒），随user_ticket一起返回")
    private Long expires_in;
}
