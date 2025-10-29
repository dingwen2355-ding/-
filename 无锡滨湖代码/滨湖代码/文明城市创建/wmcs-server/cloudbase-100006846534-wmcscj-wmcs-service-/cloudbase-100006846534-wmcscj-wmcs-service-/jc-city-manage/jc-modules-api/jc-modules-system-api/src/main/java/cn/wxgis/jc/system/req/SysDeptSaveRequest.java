package cn.wxgis.jc.system.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@ApiModel(value = "SysDeptSaveRequest", description = "部门保存 [传参]")
@Data
public class SysDeptSaveRequest {

    @ApiModelProperty(value = "部门id")
    private String id;

    @NotBlank(message = "部门名称不能为空")
    @ApiModelProperty(value = "部门名称", required = true)
    private String name;

    @ApiModelProperty(value = "父级id")
    private String parentId;

    @ApiModelProperty(value = "负责人")
    private String leader;

    @ApiModelProperty(value = "联系方式")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "显示顺序")
    private Integer orderNum;

    @ApiModelProperty(value = "类型: 1-职能部门单位; 2-区划;")
    private Integer type;

    @ApiModelProperty(value = "部门状态（0正常 1停用）")
    private Integer status;

    @ApiModelProperty(value = "备注")
    private String remark;
}
