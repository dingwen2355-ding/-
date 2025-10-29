package cn.wxgis.jc.workflow.po;

import cn.wxgis.jc.jpa.domain.comment.ColumnComment;
import cn.wxgis.jc.jpa.domain.comment.TableComment;
import cn.wxgis.jc.jpa.domain.po.BasePO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "wf_event_appeal")
@TableComment("事件申诉表")
public class WfEventAppeal extends BasePO {

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

    @Column(length = 64)
    @ColumnComment("申请部门")
    private String applyDeptId;

    @Column(length = 100)
    @ColumnComment("申请部门名称")
    private String applyDeptName;

    @Column(length = 64)
    @ColumnComment("申请人ID")
    private String applyId;

    @Column(columnDefinition = "int DEFAULT 0")
    @ColumnComment("审核状态: 0 待处理；1 通过；2不通过")
    private Integer status;

    /** 申诉时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ColumnComment("申诉时间")
    private Date appealTime;

    /** 申诉原因 */
    @Column(length = 200)
    @ColumnComment("申诉原因")
    private String reason;

    @Column(length = 200)
    @ColumnComment("审核意见")
    private String opinion;

    /** 审核时间 */
    @Column(length = 64)
    @ColumnComment("审核人")
    private String examineUserId;

    @Column(length = 64)
    @ColumnComment("审核部门")
    private String examineDeptId;

    /** 审核时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ColumnComment("审核时间")
    private Date examineTime;

    /** 新的负责单位ids */
    @Column(length = 2000)
    @ColumnComment("新责任单位ids")
    private String newDeptIds;

    /** 新的负责单位ids */
    @Column(length = 2000)
    @ColumnComment("新责任单位")
    private String newDeptName;

    /** 新的负责单位ids */
    @Column(length = 2000)
    @ColumnComment("原责任单位")
    private String oldDeptName;

}
