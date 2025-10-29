package cn.wxgis.jc.synch.wechat.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WechatUserInfoVo {

    @ApiModelProperty(value = "成员UserID")
    private String UserId;

    @ApiModelProperty(value = "成员姓名")
    private String name;

    @ApiModelProperty(value = "成员所属部门")
    private String[] department;

    @ApiModelProperty(value = "职位信息")
    private String position;

    @ApiModelProperty(value = "性别。0表示未定义，1表示男性，2表示女性")
    private Integer gender;

    @ApiModelProperty(value = "头像url。注：如果要获取小图将url最后的”/0”改成”/100”即可")
    private String avatar;

    @ApiModelProperty(value = "成员二维码图片地址")
    private String qr_code;

}
