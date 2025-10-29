package cn.wxgis.jc.workflow.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@ApiModel(value = "WfEventAppealSaveRequest", description = "事件申诉申请保存 [传参]")
@Data
public class WfEventAppealSaveRequest {

    @NotBlank(message = "任务id不能为空")
    @ApiModelProperty(value = "任务id")
    private String taskId;

    @ApiModelProperty(value = "申诉原因")
    private String applyReason;

}
