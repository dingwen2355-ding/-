package cn.wxgis.jc.workflow.po;

import cn.wxgis.jc.jpa.domain.comment.ColumnComment;
import cn.wxgis.jc.jpa.domain.comment.TableComment;
import cn.wxgis.jc.jpa.domain.po.BasePO;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "wf_event_report")
@TableComment("流程上报表")
public class WfEventReport extends BasePO {

    /** 流程id */
    @Column(length = 64)
    @ColumnComment("流程id")
    private String flowid;

    /** 流程名称 */
    @Column(length = 100)
    @ColumnComment("流程名称")
    private String flowname;

    /** 流程实例ID */
    @Column(length = 100)
    @ColumnComment("流程实例ID")
    private String flowinstanceid;

    /** 节点id */
    @Column(length = 64)
    @ColumnComment("节点id")
    private String nodeid;

    /** 节点名称 */
    @Column(length = 100)
    @ColumnComment("节点名称")
    private String nodename;

    /** 事件编号 */
    @Column(length = 50)
    @ColumnComment("事件编号")
    private String code;

    /** 事件来源 */
    @Column(length = 50)
    @ColumnComment("事件来源")
    private String eventSource;

    @Column(columnDefinition = "int DEFAULT 1")
    @ColumnComment("办结类型：1-人工办结；2-自动办结")
    private Integer endType;

    /** 紧急程度 */
    @Column(length = 50)
    @ColumnComment("紧急程度")
    private String emergency;

    /** 事件级别 */
    @Column(length = 50)
    @ColumnComment("事件级别")
    private String level;

    @Column(length = 64)
    @ColumnComment("考核周期id")
    private String cycleId;

    @Column(length = 100)
    @ColumnComment("考核周期")
    private String cycle;

    /** 事件标题 */
    @Column(length = 500)
    @ColumnComment("事件标题")
    private String eventTitle;

    /** 事件详情 */
    @Column(length = 2000)
    @ColumnComment("事件详情")
    private String content;

    /** 事发位置经纬度 */
    @Column(length = 500)
    @ColumnComment("事发位置经纬度")
    private String coordinate;

    /** 事发位置描述 */
    @Column(length = 500)
    @ColumnComment("事发位置描述")
    private String address;

    /** 手机号 */
    @Column(length = 50)
    @ColumnComment("手机号")
    private String phone;

    /** 上报人ID */
    @Column(length = 64)
    @ColumnComment("上报人ID")
    private String reporterId;

    /** 上报人 */
    @Column(length = 100)
    @ColumnComment("上报人")
    private String reportPerson;

    /** 上报时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ColumnComment("上报时间")
    private Date reportTime;

    /** 事发所属区域编码 */
    @Column(length = 64)
    @ColumnComment("事发所属区域编码")
    private String regionCode;

    /** 事发所属区域名称 */
    @Column(length = 100)
    @ColumnComment("所属区域")
    private String regionName;

    /** 事发所属区域名称 */
    @Column(length = 100)
    @ColumnComment("所属街镇")
    private String streetName;

    /** 事发所属区域名称 */
    @Column(length = 100)
    @ColumnComment("所属社区")
    private String meighborhoodName;

    /** 点位类型id */
    @Column(length = 64)
    @ColumnComment("点位类型id")
    private String checkPointsTypeId;

    /** 点位类型名称 */
    @Column(length = 100)
    @ColumnComment("点位类型名称")
    private String checkPointsType;

    /** 关联点位 **/
    @Column(length = 64)
    @ColumnComment("关联点位")
    private String checkPointsId;

    /** 点位名称 */
    @Column(length = 100)
    @ColumnComment("点位名称")
    private String checkPoints;

    /** 检查项 **/
    @Column(length = 64)
    @ColumnComment("检查项")
    private String checkItemId;

    /** 检查项 **/
    @Column(length = 500)
    @ColumnComment("检查项")
    private String checkItemName;

    /** 分数 */
    @Column(columnDefinition = "decimal(19,6)")
    @ColumnComment("分数")
    private BigDecimal score;

    /** 事件类型 */
    @Column(length = 64)
    @ColumnComment("事件类型")
    private String eventType;

    /** 事件大类 */
    @Column(length = 64)
    @ColumnComment("事件大类")
    private String eventTypeOne;

    /** 事件小类 */
    @Column(length = 64)
    @ColumnComment("事件小类")
    private String eventTypeTwo;

    /** 所属部门ids */
    @Column(length = 4000)
    @ColumnComment("所属部门ids")
    private String deptIds;

    @Column(length = 4000)
    @ColumnComment("所属部门Names")
    private String deptNames;

    @ColumnComment("状态")
    private Integer status;

    @Column(columnDefinition = "int DEFAULT 0")
    @ColumnComment("是否延期：0否；1是")
    private Integer extensionFlag;

    @Column(columnDefinition = "int DEFAULT 0")
    @ColumnComment("是否超时：0否；1是")
    private Integer timeoutFlag;

    @Column(columnDefinition = "int DEFAULT 0")
    @ColumnComment("是否转12345：0否；1是")
    private Integer citizenFlag;

    /** 处置时限 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ColumnComment("处置时限")
    private Date timelimit;


    /** 经度 */
    @Column(columnDefinition = "decimal(19,6)")
    @ColumnComment("经度")
    private BigDecimal longitude;

    /** 纬度 */
    @Column(columnDefinition = "decimal(19,6)")
    @ColumnComment("纬度")
    private BigDecimal latitude;

    @Column(length = 200)
    @ColumnComment("事件中心流程号")
    private String eventCenterFlowNo;

    @ColumnComment("告警类型")
    private String warnType;
    @Column(length = 100)
    @ColumnComment("告警设备id")
    private String channelId;
    @Column(length = 100)
    @ColumnComment("告警时间戳")
    private String timestamp;
    @Column(length = 100)
    @ColumnComment("告警设备名称")
    private String channelName;

    @Column(columnDefinition = "int DEFAULT 0")
    @ColumnComment("办结审核：1-已审核；0-待审核")
    private Integer endExamine;

}
