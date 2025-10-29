package com.jslc.modules.szbh.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author BladeX
 * @since
 */
@Data
@ApiModel(value = "回显部门对应的职位", description = "回显部门对应的职位")
public class DeptPositionDto {

    /**
     * 部门名称
     */
    @ApiModelProperty(value = "部门名称")
    private String deptName;

    /**
     * 部门id
     */
    @ApiModelProperty(value = "部门id")
    private Long deptId;

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private Long userId;

    /**
     * 职位
     */
    @ApiModelProperty(value = "职位")
    private String position;

    /**
     * 应用ids
     */
    @ApiModelProperty(value = "应用ids")
    private String appIds;
}
