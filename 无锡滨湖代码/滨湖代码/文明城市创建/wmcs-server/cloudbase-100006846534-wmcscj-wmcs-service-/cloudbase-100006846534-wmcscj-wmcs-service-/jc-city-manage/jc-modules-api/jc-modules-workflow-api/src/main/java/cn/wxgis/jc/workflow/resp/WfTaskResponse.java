package cn.wxgis.jc.workflow.resp;

import cn.wxgis.jc.workflow.po.WfEventReport;
import cn.wxgis.jc.workflow.po.WfTask;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "WfTaskResponse", description = "流程任务信息 [返回信息]")
@Data
public class WfTaskResponse extends WfTask {

    @ApiModelProperty
    private WfEventReport eventReport = new WfEventReport();
}
