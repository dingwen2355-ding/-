package cn.wxgis.jc.workflow.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@ApiModel(value = "WorkflowHandleRequest", description = "事件办理保存 [传参]")
@Data
public class WorkflowTaskRequest {

    @NotBlank(message = "流程实例不能为空")
    @ApiModelProperty(value = "流程实例ID")
    private String flowinstanceid;

    @NotBlank(message = "节点id不能为空")
    @ApiModelProperty(value = "节点id不能为空")
    private String nodeid;

    @ApiModelProperty(value = "节点处理意见")
    private String handleopinion;

    @ApiModelProperty(value = "下节点处理人ID")
    private String handlerid;

    @ApiModelProperty(value = "下节点处理人部门ID")
    private String handleDeptid;

    @ApiModelProperty(value = "节点附件ID 多个附件用逗号分隔")
    private String attchsid;

    @ApiModelProperty(value = "节点附件名称 多个附件用逗号分隔")
    private String attchnameid;

    @ApiModelProperty(value = "节点附件路径")
    private String attchpath;

    @ApiModelProperty(value = "操作类型")
    private String optTag;

    @ApiModelProperty(value = "是否开通站内信")
    private Integer messageFlag;

    @ApiModelProperty(value = "是否开通短信")
    private Integer smsFlag;


}
