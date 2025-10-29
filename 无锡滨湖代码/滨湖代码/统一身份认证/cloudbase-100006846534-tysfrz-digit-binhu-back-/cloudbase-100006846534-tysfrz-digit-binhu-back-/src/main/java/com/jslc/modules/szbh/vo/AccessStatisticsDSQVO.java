package com.jslc.modules.szbh.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author BladeX
 * @since
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "访问统计", description = "访问统计")
public class AccessStatisticsDSQVO {



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
    @ApiModelProperty(value = "访问数量")
    private String viewNum;




}
