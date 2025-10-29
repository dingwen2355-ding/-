package cn.wxgis.jc.workflow.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@ApiModel(value = "WorkflowHandleRequest", description = "事件办理保存 [传参]")
@Data
public class WorkflowUpdateRequest {

    @NotBlank(message = "流程实例不能为空")
    @ApiModelProperty(value = "流程实例ID")
    private String flowinstanceid;

    @NotBlank(message = "任务id不能为空")
    @ApiModelProperty(value = "任务id")
    private String taskId;

    @ApiModelProperty(value = "查看范围：空为查自身相关，all为查询全部")
    private String scope;

}
