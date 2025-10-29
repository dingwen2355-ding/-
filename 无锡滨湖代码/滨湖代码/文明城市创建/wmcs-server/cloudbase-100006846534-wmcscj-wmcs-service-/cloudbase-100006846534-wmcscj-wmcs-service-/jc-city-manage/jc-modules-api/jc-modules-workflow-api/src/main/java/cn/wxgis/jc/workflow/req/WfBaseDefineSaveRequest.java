package cn.wxgis.jc.workflow.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@ApiModel(value = "WfBaseDefineSaveRequest", description = "工作流程保存 [传参]")
@Data
public class WfBaseDefineSaveRequest {

    @ApiModelProperty(value = "id")
    private String id;

    @NotBlank(message = "流程id不能为空")
    @ApiModelProperty(value = "流程id")
    private String flowid;

    @NotBlank(message = "流程名称不能为空")
    @ApiModelProperty(value = "流程名称")
    private String flowname;

    @ApiModelProperty(value = "表单路径")
    private String formpath;
}
