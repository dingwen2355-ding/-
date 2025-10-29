package com.jslc.modules.szbh.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author BladeX
 * @since
 */
@Data
@ApiModel(value = "在线用户", description = "在线用户")
public class UserOnlineVO {

    /**
     * app登录记录id
     */
    @ApiModelProperty(value = "app登录记录id")
    private Long id;

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private Long userId;


    /**
     * 应用id
     */
    @ApiModelProperty(value = "应用id")
    private Long appId;



    /**
     * 部门id
     */
    @ApiModelProperty(value = "部门id")
    private Long deptId;


    /**
     * 用户名称
     */
    @ApiModelProperty(value = "用户名称")
    private String userName;

    /**
     * 用户账户
     */
    @ApiModelProperty(value = "用户账户")
    private String userAccount;

    /**
     * 登录数量
     */
    @ApiModelProperty(value = "登录数量")
    private Integer onLineSum;


    /**
     * 用户头像
     */
    @ApiModelProperty(value = "用户头像")
    private String avatar;


    /**
     * 部门名称
     */
    @ApiModelProperty(value = "部门名称")
    private String bhDeptName;

    /**
     * 职位名称
     */
    @ApiModelProperty(value = "职位名称")
    private String positionName;


    /**
     * 登录ip
     */
    @ApiModelProperty(value = "登录ip")
    private String ip;

    /**
     * 应用名称
     */
    @ApiModelProperty(value = "应用名称")
    private String appName;


    /**
     * 浏览器
     */
    @ApiModelProperty(value = "浏览器")
    private String browser;

    /**
     * 登录设备
     */
    @ApiModelProperty(value = "登录设备")
    private String device;


    /**
     * token
     */
    @ApiModelProperty(value = "token")
    private String token;


    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;





}
