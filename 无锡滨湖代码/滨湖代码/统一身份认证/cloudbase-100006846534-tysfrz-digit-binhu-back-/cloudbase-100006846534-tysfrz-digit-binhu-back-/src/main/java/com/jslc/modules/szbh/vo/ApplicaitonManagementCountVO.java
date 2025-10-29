package com.jslc.modules.szbh.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author BladeX
 * @since
 */
@Data
@ApiModel(value = "用户活跃度", description = "用户活跃度")
public class ApplicaitonManagementCountVO {

    @ApiModelProperty(value = "应用数量")
    private Integer yyNum;

    @ApiModelProperty(value = "组件数量")
    private Integer zjNum;

    @ApiModelProperty(value = "工具数量")
    private Integer gjNum;
}
