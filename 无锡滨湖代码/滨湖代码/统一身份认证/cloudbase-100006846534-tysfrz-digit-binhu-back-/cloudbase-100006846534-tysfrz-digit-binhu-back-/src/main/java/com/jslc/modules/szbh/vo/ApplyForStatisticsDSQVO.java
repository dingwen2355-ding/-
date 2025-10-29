package com.jslc.modules.szbh.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author BladeX
 * @since
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "申请统计", description = "申请统计")
public class ApplyForStatisticsDSQVO {



    /**
     * 应用id
     */
    @ApiModelProperty(value = "应用id")
    private Long id;

    /**
     * 应用名称
     */
    @ApiModelProperty(value = "应用名称")
    private String applicationName;
    /**
     * app_id
     */
    @ApiModelProperty(value = "app_id")
    private String appId;

    /**
     * app_secret
     */
    @ApiModelProperty(value = "app_secret")
    private String appSecret;


    /**
     * 申请数量
     */
    @ApiModelProperty(value = "申请数量")
    private String applyForNum;




}
