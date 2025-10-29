package cn.wxgis.jc.workflow.web.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 事件统计信息
 */
@ApiModel(value = "EventCountVo", description = "事件统计信息信息")
@Data
public class EventCountVo {

    @ApiModelProperty(value = "责任部门名称")
    private String deptName;

    @ApiModelProperty(value = "工单总数")
    private Integer count = 0;

    @ApiModelProperty(value = "办结总数")
    private Integer endCount = 0;

    @ApiModelProperty(value = "超时总数")
    private Integer timeoutCount = 0;

    @ApiModelProperty(value = "黄牌告警数")
    private Integer yellowWarn = 0;

    @ApiModelProperty(value = "红色告警数")
    private Integer redWarn = 0;

}
