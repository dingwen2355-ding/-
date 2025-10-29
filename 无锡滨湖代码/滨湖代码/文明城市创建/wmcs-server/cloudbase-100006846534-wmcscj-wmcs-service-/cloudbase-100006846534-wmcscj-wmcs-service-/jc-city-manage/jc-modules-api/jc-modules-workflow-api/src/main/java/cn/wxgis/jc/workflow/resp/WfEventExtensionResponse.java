package cn.wxgis.jc.workflow.resp;

import cn.wxgis.jc.workflow.po.WfEventExtension;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@ApiModel(value = "WfExtensionResponse", description = "流程延期申请信息 [返回信息]")
@Data
public class WfEventExtensionResponse extends WfEventExtension {

    @ApiModelProperty(value = "事发所属区域名称")
    private String regionName;

    @ApiModelProperty(value = "责任部门名称")
    private String deptNames;

    @ApiModelProperty(value = "事件来源")
    private Integer eventSource;

    @ApiModelProperty(value = "点位类型")
    private String checkPointsType;

    @ApiModelProperty(value = "点位")
    private String checkPoints;

    @ApiModelProperty(value = "检查项")
    private String checkItem;

    @ApiModelProperty(value = "事件子类")
    private String eventTypeName;

    @ApiModelProperty(value = "事件大类")
    private String eventTypeOneName;

    @ApiModelProperty(value = "事件小类")
    private String eventTypeTwoName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "节点处置时限")
    private Date timelimit;

    @ApiModelProperty(value = "延期状态：1-申请中；2-已通过；0-已拒绝/未申请")
    private Integer extensionStatus;

    @ApiModelProperty(value = "纬度")
    private BigDecimal latitude;

    @ApiModelProperty(value = "经度")
    private BigDecimal longitude;


    @ApiModelProperty(value = "事件编码")
    private String code;

    @ApiModelProperty(value = "周期")
    private String cycle;

    @ApiModelProperty(value = "事件id")
    private String eventId;

    @ApiModelProperty(value = "申请人名称")
    private String applyPersonName;

    @ApiModelProperty(value = "事件详情")
    private String content;

    @ApiModelProperty(value = "分数")
    private BigDecimal score;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "上报时间")
    private Date reportTime;

    @ApiModelProperty(value = "节点名称")
    private String nodename;

    @ApiModelProperty(value = "事件状态")
    private String eventStatus;

    @ApiModelProperty(value = "处理部门")
    private String handleDept;

    @ApiModelProperty(value = "处理节点")
    private String handleNodename;

    @ApiModelProperty(value = "处理状态")
    private Integer handleStatus;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "处理时限")
    private Date handleTimelimit;

}
