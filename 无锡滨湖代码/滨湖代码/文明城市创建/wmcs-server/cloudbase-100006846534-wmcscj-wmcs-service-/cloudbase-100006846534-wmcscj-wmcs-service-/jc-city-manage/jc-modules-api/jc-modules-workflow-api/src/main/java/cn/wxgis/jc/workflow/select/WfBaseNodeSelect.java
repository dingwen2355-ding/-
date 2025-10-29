package cn.wxgis.jc.workflow.select;

import cn.wxgis.jc.common.domain.param.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "WfBaseNodeSelect", description = "流程节点查询 [传参]")
@Data
public class WfBaseNodeSelect extends PageParam {

    @ApiModelProperty(value = "流程id")
    private String flowid;

}
