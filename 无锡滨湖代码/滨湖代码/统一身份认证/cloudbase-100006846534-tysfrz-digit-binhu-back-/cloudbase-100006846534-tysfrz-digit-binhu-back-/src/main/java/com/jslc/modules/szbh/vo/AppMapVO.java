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
@ApiModel(value = "应用id名称", description = "应用id名称")
public class AppMapVO {

    /**
     * app id
     */
    @ApiModelProperty(value = "appId")
    private String appId;

    /**
     * 应用名称
     */
    @ApiModelProperty(value = "应用名称")
    private String appName;


    /**
     * app访问次数
     */
    @ApiModelProperty(value = "app访问次数")
    private Integer appRecordSum;




}
