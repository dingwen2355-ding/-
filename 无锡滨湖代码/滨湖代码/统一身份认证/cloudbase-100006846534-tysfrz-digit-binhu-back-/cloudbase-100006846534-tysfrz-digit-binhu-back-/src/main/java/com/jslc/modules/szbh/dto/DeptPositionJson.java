package com.jslc.modules.szbh.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author BladeX
 * @since
 */
@Data
@ApiModel(value = "DeptPositionJson", description = "用于接收前端传来的JSON串")
public class DeptPositionJson {
    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private Long userId;

    /**
     * 部门id
     */
    @ApiModelProperty(value = "部门id")
    private Long deptId;

    /**
     * 应用ids
     */
    @ApiModelProperty(value = "应用ids")
    private String appIds;
}
