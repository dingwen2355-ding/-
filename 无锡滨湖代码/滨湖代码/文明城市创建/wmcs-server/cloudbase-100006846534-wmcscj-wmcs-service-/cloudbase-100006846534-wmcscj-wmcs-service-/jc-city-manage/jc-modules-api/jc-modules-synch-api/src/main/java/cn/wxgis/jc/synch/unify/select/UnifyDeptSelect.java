package cn.wxgis.jc.synch.unify.select;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "UnifyDeptSelect", description = "第三方部门查询 [传参]")
@Data
public class UnifyDeptSelect {

    @ApiModelProperty(value = "部门id")
    private String id;

    @ApiModelProperty(value = "部门名称")
    private String name;

    @ApiModelProperty(value = "上级部门")
    private String parentId;

}
