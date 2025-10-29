package cn.wxgis.jc.workflow.vo;

import cn.wxgis.jc.system.po.SysUser;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 上报事件参数信息
 */
@ApiModel(value = "EventReportVO", description = "上报事件参数信息")
@Data
public class EventReportVO {

    @ApiModelProperty(value = "事件编号")
    private String code;

    @ApiModelProperty(value = "流程id")
    private String flowid;

    @ApiModelProperty(value = "节点id")
    private String nodeid;

    @ApiModelProperty(value = "路由id")
    private String routeid;

    @ApiModelProperty(value = "结束节点")
    private String endnodeid;

    @ApiModelProperty(value = "所属区划")
    private String regionCode;

    @ApiModelProperty(value = "区划名称")
    private String regionName;

    @ApiModelProperty(value = "考核周期id")
    private String cycleId;

    @ApiModelProperty(value = "考核周期")
    private String cycle;

    @ApiModelProperty(value = "事件来源")
    private String eventSource;

    @ApiModelProperty(value = "紧急程度")
    private String emergency;

    @ApiModelProperty(value = "事件级别")
    private String level;

    @ApiModelProperty(value = "点位类型id")
    private String checkPointsTypeId;

    @ApiModelProperty(value = "点位类型名称")
    private String checkPointsType;

    @ApiModelProperty(value = "关联点位")
    private String checkPointsId;

    @ApiModelProperty(value = "点位名称")
    private String checkPoints;

    @ApiModelProperty(value = "检查项id")
    private String checkItemId;

    @ApiModelProperty(value = "检查项内容")
    private String checkItemName;

    @ApiModelProperty(value = "事件标题（检查项）")
    private String eventTitle;

    @ApiModelProperty(value = "分数")
    private BigDecimal score;

    @ApiModelProperty(value = "事件详情")
    private String content;

    @ApiModelProperty(value = "事发位置经纬度")
    private String coordinate;

    @ApiModelProperty(value = "事发位置描述")
    private String address;

    @ApiModelProperty(value = "事件类型")
    private String eventType;

    @ApiModelProperty(value = "事件大类")
    private String eventTypeOne;

    @ApiModelProperty(value = "事件小类")
    private String eventTypeTwo;

    @ApiModelProperty(value = "责任单位id")
    private String deptIds;

    @ApiModelProperty(value = "责任单位")
    private String deptNames;

    @ApiModelProperty(value = "处理人id")
    private String handlerid;

    @ApiModelProperty(value = "处理人部门ID")
    private String handlerDeptId;

    @ApiModelProperty(value = "节点附件名称 多个附件用逗号分隔")
    private String attchnameid;

    @ApiModelProperty(value = "节点附件路径")
    private String attchpath;

    @ApiModelProperty(value = "处理人员列表")
    private List<SysUser> people;

    @ApiModelProperty(value = "标识 1.成功 2.选人  3.流程错误")
    private String flag;

    @ApiModelProperty(value = "流程名称")
    private String flowname;

    @ApiModelProperty(value = "路由类型")
    private Integer routetype;

    @ApiModelProperty(value = "流程实例")
    private String flowinstanceid;

    @ApiModelProperty(value = "所属区域")
    private String cityName;

    @ApiModelProperty(value = "所属街镇")
    private String streetName;

    @ApiModelProperty(value = "所属社区")
    private String meighborhoodName;

    @ApiModelProperty(value = "当前节点名称")
    private String currentNodeName;

    @ApiModelProperty(value = "事件状态")
    private Integer status;

    @ApiModelProperty(value = "处置时限")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date timelimit;

    /** 经度 */
    @ApiModelProperty(value = "经度")
    private String longitude;

    /** 纬度 */
    @ApiModelProperty(value = "纬度")
    private String latitude;

    @ApiModelProperty(value = "事件中心绑定事项类型")
    private String eventCenterTypeId;
    @ApiModelProperty(value = "告警类型")
    private String warnType;
    @ApiModelProperty(value = "告警时间戳")
    private String timestamp;
    @ApiModelProperty(value = "告警设备id")
    private String channelId;
    @ApiModelProperty(value = "告警设备名称")
    private String channelName;

}
