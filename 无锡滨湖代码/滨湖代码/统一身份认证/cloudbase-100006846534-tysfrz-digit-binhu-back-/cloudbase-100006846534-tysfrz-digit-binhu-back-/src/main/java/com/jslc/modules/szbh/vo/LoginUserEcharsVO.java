package com.jslc.modules.szbh.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author BladeX
 * @since
 */
@Data
@ApiModel(value = "用户活跃分析echars", description = "用户活跃分析echars")
public class LoginUserEcharsVO {


    /**
     * 用户总数
     */
    @ApiModelProperty(value = "用户总数")
    private List times;



    /**
     * 用户总数
     */
    @ApiModelProperty(value = "用户总数")
    private List userSum;

    /**
     * 新增用户
     */
    @ApiModelProperty(value = "新增用户")
    private List createUserSum;

    /**
     * 活跃用户
     */
    @ApiModelProperty(value = "活跃用户")
    private List activeUserSum;


    /**
     * 登录数量
     */
    @ApiModelProperty(value = "登录数量")
    private List onLineSum;





}
