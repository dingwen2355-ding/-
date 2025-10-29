package com.jslc.modules.szbh.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author BladeX
 * @since
 */
@Data
@ApiModel(value = "在线用户", description = "在线用户")
@AllArgsConstructor
public class HeadAppInfoVO {

    /**
     * 应用id
     */
    @ApiModelProperty(value = "应用id")
    private String appId;

    /**
     * 应用名称
     */
    @ApiModelProperty(value = "应用名称")
    private String appName;




}
