package cn.wxgis.jc.workflow.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@ApiModel(value = "WfEventReportRequest", description = "事件上报保存 [传参]")
@Data
public class WorkflowReportRequest {

    @NotBlank(message = "流程不能为空")
    @ApiModelProperty(value = "流程id")
    private String flowid;

    @NotBlank(message = "开始节点不能为空")
    @ApiModelProperty(value = "开始节点")
    private String startnodeid;

    @NotBlank(message = "结束节点不能为空")
    @ApiModelProperty(value = "结束节点")
    private String endnodeid;

    @ApiModelProperty(value = "所属区划")
    private String regionCode;

    @ApiModelProperty(value = "区域名称")
    private String regionName;

    @ApiModelProperty(value = "考核周期id")
    private String cycleId;

    @ApiModelProperty(value = "考核周期")
    private String cycle;

    @ApiModelProperty(value = "紧急程度")
    private String emergency;

    @ApiModelProperty(value = "事件级别")
    private String level;

    @NotBlank(message = "点位类型不能为空")
    @ApiModelProperty(value = "点位类型id")
    private String checkPointsTypeId;

    @NotBlank(message = "点位不能为空")
    @ApiModelProperty(value = "关联点位")
    private String checkPointsId;

    @NotBlank(message = "检查项不能为空")
    @ApiModelProperty(value = "检查项id")
    private String checkItemId;

    @NotBlank(message = "检查项不能为空")
    @ApiModelProperty(value = "事件标题（检查项）")
    private String eventTitle;

    @ApiModelProperty(value = "分数")
    private BigDecimal score;

    @NotBlank(message = "内容不能为空")
    @ApiModelProperty(value = "事件详情")
    private String content;

    @ApiModelProperty(value = "事发位置经纬度")
    private String coordinate;

    @ApiModelProperty(value = "事发位置描述")
    private String address;

    @NotBlank(message = "事件大类不能为空")
    @ApiModelProperty(value = "事件大类")
    private String eventTypeOne;

    @NotBlank(message = "事件小类不能为空")
    @ApiModelProperty(value = "事件小类")
    private String eventTypeTwo;

    @NotBlank(message = "事件类型不能为空")
    @ApiModelProperty(value = "事件类型")
    private String eventType;

    @NotBlank(message = "责任单位不能为空")
    @ApiModelProperty(value = "责任单位id")
    private String deptIds;

    @ApiModelProperty(value = "处理人id")
    private String handlerid;

    @ApiModelProperty(value = "节点附件名称 多个附件用逗号分隔")
    private String attchnameid;

    @ApiModelProperty(value = "节点附件路径")
    private String attchpath;

    /** 经度 */
    @ApiModelProperty(value = "经度")
    private String longitude;

    /** 纬度 */
    @ApiModelProperty(value = "纬度")
    private String latitude;

    @ApiModelProperty(value = "事件来源")
    private String eventSource;

    @ApiModelProperty(value = "告警类型")
    private String warnType;
    @ApiModelProperty(value = "告警时间戳")
    private String timestamp;
    @ApiModelProperty(value = "告警设备id")
    private String channelId;
    @ApiModelProperty(value = "告警设备名称")
    private String channelName;
}
