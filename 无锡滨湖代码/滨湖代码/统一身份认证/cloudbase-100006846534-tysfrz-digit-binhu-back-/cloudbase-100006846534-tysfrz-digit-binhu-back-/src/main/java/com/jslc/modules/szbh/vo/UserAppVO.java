package com.jslc.modules.szbh.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author BladeX
 * @since
 */
@Data
@ApiModel(value = "应用访问关系", description = "应用访问关系")
public class UserAppVO {



    /**
     * 账号
     */
    @ApiModelProperty(value = "账号")
    private String account;


    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名")
    private String name;

    /**
     * 部门名称
     */
    @ApiModelProperty(value = "部门名称")
    private String deptName;


    /**
     * 职位名称
     */
    @ApiModelProperty(value = "职位名称")
    private String positionName;

    /**
     * 是否可登录
     */
    @ApiModelProperty(value = "是否可登录")
    private String isEnableLoginName;


    /**
     * 应用名称
     */
    @ApiModelProperty(value = "应用名称")
    private String appName;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    /**
     * 类型
     */
    @ApiModelProperty(value = "类型")
    private Integer type;

}
