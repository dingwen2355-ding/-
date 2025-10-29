package cn.wxgis.jc.common.domain.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class LoginBodyRes {

    /**
     * 用户名
     */
    @ApiModelProperty(value = "登录账号", required = true, example = "account")
    @NotBlank(message = "请输入登录名")
    @Size(min = 1, max = 200)
    private String username;

    /**
     * 用户密码
     */
    @ApiModelProperty(value = "密码", required = true, example = "password")
    @NotBlank(message = "请输入密码")
    private String password;

    /**
     * 验证码
     */
    @ApiModelProperty(value = "验证码", example = "captcha")
    private String captcha;


    @ApiModelProperty(required = true, value = "登陆终端: 0: PC端; 1: APP端", example = "0，1", allowableValues = "0，1")
    private Long channel;

}
