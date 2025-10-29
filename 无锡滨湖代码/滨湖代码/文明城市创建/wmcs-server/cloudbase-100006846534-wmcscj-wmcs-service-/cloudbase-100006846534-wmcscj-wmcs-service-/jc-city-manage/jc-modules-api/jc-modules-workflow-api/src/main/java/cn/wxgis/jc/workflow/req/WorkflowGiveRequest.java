package cn.wxgis.jc.workflow.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@ApiModel(value = "WfEventReportRequest", description = "事件上报保存 [传参]")
@Data
public class WorkflowGiveRequest {

    @NotBlank(message = "事件id不能为空")
    @ApiModelProperty(value = "事件id")
    private String id;

    @NotBlank(message = "责任单位不能为空")
    @ApiModelProperty(value = "分派的责任单位")
    private String deptIds;
}
