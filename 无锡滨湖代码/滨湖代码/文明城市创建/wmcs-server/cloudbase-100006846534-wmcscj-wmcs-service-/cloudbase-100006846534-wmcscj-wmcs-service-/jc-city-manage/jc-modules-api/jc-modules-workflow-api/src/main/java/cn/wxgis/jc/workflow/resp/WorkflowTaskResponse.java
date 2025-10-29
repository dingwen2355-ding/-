package cn.wxgis.jc.workflow.resp;

import cn.wxgis.jc.workflow.po.WfTask;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@ApiModel(value = "WorkflowHandleResponse", description = "流程待办信息 [返回信息]")
@Data
public class WorkflowTaskResponse extends WfTask {

    @ApiModelProperty(value = "事件任务id")
    private String taskid;

    @ApiModelProperty(value = "点位类型id")
    private String checkPointsTypeId;

    @ApiModelProperty(value = "点位类型")
    private String checkPointsType;

    @ApiModelProperty(value = "关联点位id")
    private String checkPointsId;

    @ApiModelProperty(value = "关联点位")
    private String checkPoints;

    @ApiModelProperty(value = "扣分")
    private BigDecimal score;

    @ApiModelProperty(value = "事发位置经纬度")
    private String coordinate;

    @ApiModelProperty(value = "事发位置描述")
    private String address;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "上报时间")
    private Date reportTime;

    @ApiModelProperty(value = "事件来源")
    private String eventSource;

    @ApiModelProperty(value = "紧急程度")
    private String emergency;

    @ApiModelProperty(value = "事件级别")
    private String level;

    @ApiModelProperty(value = "事发所属区域编码")
    private String regionCode;

    @ApiModelProperty(value = "事发所属区域名称")
    private String regionName;

    @ApiModelProperty(value = "所属区域")
    private String cityName;

    @ApiModelProperty(value = "所属街镇")
    private String streetName;

    @ApiModelProperty(value = "所属社区")
    private String meighborhoodName;

    @ApiModelProperty(value = "事件详情")
    private String content;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "上报人ID")
    private String reporterId;

    @ApiModelProperty(value = "上报人姓名")
    private String reportPerson;

    @ApiModelProperty(value = "职责部门id")
    private String deptIds;

    @ApiModelProperty(value = "职责部门名称")
    private String deptNames;

    @ApiModelProperty(value = "事件类型")
    private String eventType;

    @ApiModelProperty(value = "事件子类")
    private String eventTypeName;

    @ApiModelProperty(value = "事件大类")
    private String eventTypeOne;

    @ApiModelProperty(value = "事件大类")
    private String eventTypeOneName;

    @ApiModelProperty(value = "事件小类")
    private String eventTypeTwo;

    @ApiModelProperty(value = "事件小类")
    private String eventTypeTwoName;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "附件地址")
    private String attchpath;

}
