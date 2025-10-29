package cn.wxgis.jc.workflow.select;

import cn.wxgis.jc.common.domain.param.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "WorkflowUserSelect", description = "流程处理用户查询 [传参]")
@Data
public class WorkflowDetailSelect extends PageParam {

    @ApiModelProperty(value = "流程id")
    private String flowid;

    @ApiModelProperty(value = "节点id")
    private String nodeid;

    @ApiModelProperty(value = "流程实例id")
    private String flowInstanceId;

    @ApiModelProperty(value = "部门id")
    private String deptIds;
}
