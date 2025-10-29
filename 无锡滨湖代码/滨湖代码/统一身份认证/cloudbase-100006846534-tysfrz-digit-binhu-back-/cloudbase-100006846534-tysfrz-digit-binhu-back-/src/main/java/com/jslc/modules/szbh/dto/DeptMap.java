package com.jslc.modules.szbh.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author BladeX
 * @since
 */
@Data
@ApiModel(value = "人员对应的职位授权的 应用", description = "人员对应的职位授权的 应用")
public class DeptMap {
    /**
     * 部门id
     */
    @ApiModelProperty(value = "部门id")
    private String deptId;

    /**
     * 部门id
     */
    @ApiModelProperty(value = "部门id")
    private List<Long> appIds;
}
