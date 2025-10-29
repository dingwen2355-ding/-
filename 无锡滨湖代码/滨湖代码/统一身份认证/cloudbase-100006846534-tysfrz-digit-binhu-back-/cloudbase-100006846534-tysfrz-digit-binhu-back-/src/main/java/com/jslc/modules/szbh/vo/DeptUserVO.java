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
@ApiModel(value = "部门人员信息", description = "部门人员信息")
public class DeptUserVO {



    /**
     * 人员名称
     */
    @ApiModelProperty(value = "人员名称")
    private String userName;


    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    private String phone;

    /**
     * 部门名称
     */
    @ApiModelProperty(value = "部门名称")
    private String deptName;


}
