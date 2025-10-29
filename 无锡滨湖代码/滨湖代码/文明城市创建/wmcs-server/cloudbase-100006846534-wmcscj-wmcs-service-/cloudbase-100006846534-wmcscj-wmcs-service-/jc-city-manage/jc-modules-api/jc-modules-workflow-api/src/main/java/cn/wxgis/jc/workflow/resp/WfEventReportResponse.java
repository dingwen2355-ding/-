package cn.wxgis.jc.workflow.resp;

import cn.wxgis.jc.common.util.StringUtils;
import cn.wxgis.jc.jpa.domain.comment.ColumnComment;
import cn.wxgis.jc.workflow.po.WfEventAppeal;
import cn.wxgis.jc.workflow.po.WfEventExtension;
import cn.wxgis.jc.workflow.po.WfEventReport;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ApiModel(value = "WfEventReportResponse", description = "流程事件详情信息 [返回信息]")
@Data
public class WfEventReportResponse extends WfEventReport {

    @ApiModelProperty(value = "任务id")
    private String taskid;

    @ApiModelProperty(value = "部门处置id")
    private String eventDeptId;

    @ApiModelProperty(value = "事发所属区域名称")
    private String regionName;

    @ApiModelProperty(value = "责任部门名称")
    private String deptNames;

    @ApiModelProperty(value = "点位类型")
    private String checkPointsType;

    @ApiModelProperty(value = "点位")
    private String checkPoints;

    @ApiModelProperty(value = "检查项ID")
    private String checkItemId;

    @ApiModelProperty(value = "检查项名称")
    private String checkItemName;

    @ApiModelProperty(value = "事件子类")
    private String eventTypeName;

    @ApiModelProperty(value = "事件大类")
    private String eventTypeOneName;

    @ApiModelProperty(value = "事件小类")
    private String eventTypeTwoName;

    @ApiModelProperty(value = "附件地址")
    private String attchpath;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "节点处置时限")
    private Date timelimit;

    @ApiModelProperty(value = "延期状态：1-申请中；2-已通过；0-已拒绝/未申请")
    private Integer extensionStatus;

    @ApiModelProperty(value = "申诉状态：1-申请中；2-已通过；0-已拒绝/未申请")
    private Integer appealStatus;

    @ApiModelProperty(value = "纬度")
    private BigDecimal latitude;

    @ApiModelProperty(value = "经度")
    private BigDecimal longitude;

    @ApiModelProperty(value = "处理部门")
    private String handleDept;

    @ApiModelProperty(value = "处理节点")
    private String handleNodename;

    @ApiModelProperty(value = "处理状态")
    private Integer handleStatus;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "处理时限")
    private Date handleTimelimit;

    @ApiModelProperty(value = "处理是否超时")
    private Integer hadndleTimeoutFlag;


    @ApiModelProperty(value = "处理部门")
    private String handlerDeptName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "结束时间")
    private Date endtime;

    @ApiModelProperty(value = "延期列表")
    private List<WfEventExtension> extension;

    @ApiModelProperty(value = "是否申诉")
    private Integer appealFlag;

    @ApiModelProperty(value = "申诉列表")
    private List<WfEventAppeal> appeal = new ArrayList<>();

}
