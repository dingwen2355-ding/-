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
@Table(name = "wf_node_history")
@TableComment("节点历史表")
public class WfNodeHistory extends BasePO {

    /** 流程id */
    @Column(length = 64)
    @ColumnComment("流程id")
    private String flowid;

    /** 流程名称 */
    @Column(length = 500)
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

    /** 处理人ID */
    @Column(length = 64)
    @ColumnComment("处理人ID")
    private String handlerid;

    @Column(length = 100)
    @ColumnComment("处理人名称")
    private String handler;

    /** 受理部门 */
    @Column(length = 100)
    @ColumnComment("受理单位id")
    private String handleDeptid;

    /** 受理部门 */
    @Column(length = 100)
    @ColumnComment("受理单位")
    private String handleDept;

    /** 节点抄送人ID 多人用逗号分隔 */
    @Column(length = 2000)
    @ColumnComment("节点抄送人ID 多人用逗号分隔")
    private String cc;

    /** 节点处置时限 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ColumnComment("节点处置时限")
    private Date timelimit;

    /** 受理时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ColumnComment("受理时间")
    private Date handletime;

    /** 节点处理意见 */
    @Column(length = 2000)
    @ColumnComment("节点处理意见")
    private String handleopinion;

    /** 节点附件ID 多个附件用逗号分隔 */
    @Column(length = 2000)
    @ColumnComment("节点附件ID 多个附件用逗号分隔")
    private String attchsid;

    /** 节点附件名称 多个附件用逗号分隔 */
    @Column(length = 2000)
    @ColumnComment("节点附件名称 多个附件用逗号分隔")
    private String attchnameid;

    /** 节点附件路径 */
    @Column(length = 2000)
    @ColumnComment("节点附件路径")
    private String attchpath;

    /** 节点处理类型
     * 1：单一签核、
     * 2：多人单一签核、
     * 3：多人处理、
     * 4：多人顺序 */
    @ColumnComment("节点处理类型 1：单一签核、2：多人单一签核、3：多人处理、4：多人顺序")
    private Integer nodehandletype;

    /** 路由类型
     * 1：提交
     * 2：退回
     */
    @ColumnComment("路由类型 1：提交 2：退回")
    private Integer routetype;

    @Column(length = 64)
    @ColumnComment("部门处置事件id")
    private String deptEventId;

    @ColumnComment("是否开通站内信")
    private Integer messageFlag;

    @ColumnComment("是否开通短信")
    private Integer smsFlag;

}
