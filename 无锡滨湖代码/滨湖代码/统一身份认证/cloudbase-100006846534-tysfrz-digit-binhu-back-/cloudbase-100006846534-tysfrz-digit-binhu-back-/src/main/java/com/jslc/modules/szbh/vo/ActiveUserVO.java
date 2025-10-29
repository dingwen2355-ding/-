package com.jslc.modules.szbh.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author BladeX
 * @since
 */
@Data
@ApiModel(value = "用户活跃度", description = "用户活跃度")
public class ActiveUserVO {



    /**
     * 日期
     */
    @ApiModelProperty(value = "日期")
    private String time;


    /**
     * 用户总数
     */
    @ApiModelProperty(value = "用户总数")
    private Integer userSum;

    /**
     * 新增用户
     */
    @ApiModelProperty(value = "新增用户")
    private Integer createUserSum;

    /**
     * 活跃用户
     */
    @ApiModelProperty(value = "活跃用户")
    private String activeUserSum;

    /**
     * 活跃度
     */
    @ApiModelProperty(value = "活跃度")
    private String activePercent;

    /**
     * 登录数量
     */
    @ApiModelProperty(value = "登录数量")
    private Integer onLineSum;


    /**
     * 平均登录数量
     */
    @ApiModelProperty(value = "平均登录数量")
    private BigDecimal avgOnLineSum;


}
