package cn.wxgis.jc.workflow.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "WorkflowApiSaveRequest", description = "第三方调用事件处置接口 [传参]")
@Data
public class WorkflowApiSaveRequest {

    @ApiModelProperty(value = "事件中心流程号")
    private String eventCenterFlowNo;

    @ApiModelProperty(value = "方法：Node2-街镇/职能部门受理。Node3-社区整改")
    private String nodeid;

    @ApiModelProperty(value = "处置部门id")
    private String handleDeptid;

    @ApiModelProperty(value = "审批意见")
    private String opinion;

    @ApiModelProperty(value = "附件路径")
    private String attchpath;


}
