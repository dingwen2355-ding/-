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
@Table(name = "wf_dept_event")
@TableComment("单位事件表")
public class WfDeptEvent extends BasePO {

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

    @Column(columnDefinition = "int DEFAULT 0")
    @ColumnComment("是否延期：0否；1是")
    private Integer extensionFlag;

    @Column(columnDefinition = "int DEFAULT 0")
    @ColumnComment("是否超时：0否；1是")
    private Integer timeoutFlag;

    /** 受理单位类型：1职能部门；2区划部门 */
    @ColumnComment("受理单位类型：1职能部门；2区划部门")
    private Integer handlerDeptType;

    /** 处置部门id */
    @Column(length = 64)
    @ColumnComment("处置人部门id")
    private String handlerDeptId;

    /** 处置时限 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ColumnComment("处置时限")
    private Date timelimit;

    /** 处置部门id */
    @Column(length = 64)
    @ColumnComment("处置部门id")
    private String handleDeptid;

    /** 处置部门 */
    @Column(length = 100)
    @ColumnComment("处置部门")
    private String handleDept;

    @ColumnComment("状态")
    private Integer status;
}
