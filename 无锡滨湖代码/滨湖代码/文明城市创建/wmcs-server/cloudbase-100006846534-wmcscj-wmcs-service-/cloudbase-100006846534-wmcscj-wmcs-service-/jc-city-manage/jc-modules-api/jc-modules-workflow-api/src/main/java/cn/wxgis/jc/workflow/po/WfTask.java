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
@Table(name = "wf_task")
@TableComment("事件受理表")
public class WfTask extends BasePO {

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

    /** 节点处理类型
     * 1：单一签核、
     * 2：多人单一签核、
     * 3：多人处理、
     * 4：多人顺序 */
    @ColumnComment("节点处理类型 1：单一签核、2：多人单一签核、3：多人处理、4：多人顺序")
    private Integer nodehandletype;

    /** 节点处理人ID */
    @Column(length = 100)
    @ColumnComment("节点处理人ID")
    private String handlerid;
    /** 节点处理人ID */
    @Column(length = 100)
    @ColumnComment("节点处理人名称")
    private String handler;

    /** 受理单位id */
    @Column(length = 64)
    @ColumnComment("受理单位id")
    private String handleDeptid;
    /** 受理单位id */
    @Column(length = 100)
    @ColumnComment("受理单位")
    private String handleDept;

    /** 处理顺序  给多人顺序处理用 */
    @ColumnComment("处理顺序  给多人顺序处理用")
    private Integer handleorder;

    /** 节点发送人ID 是谁发送来过的任务 */
    @Column(length = 64)
    @ColumnComment("节点发送人ID 是谁发送来过的任务")
    private String senderid;

    /** 节点发送时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ColumnComment("节点发送时间")
    private Date senttime;

    /** 节点处置时限 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ColumnComment("节点处置时限")
    private Date timelimit;

    /** 路由类型
     * 1：提交
     * 2：退回
     */
    @ColumnComment("路由类型 1：提交 2：退回")
    private Integer routetype;

    @ColumnComment("部门类型")
    private Integer deptTypeId;
    @Column(length = 64)
    @ColumnComment("部门事件id")
    private String deptEventId;
    @Column(length = 64)
    @ColumnComment("部门id")
    private String deptId;
}
