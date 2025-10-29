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
@Table(name = "wf_instance")
@TableComment("流程实例表")
public class WfInstance extends BasePO {

    /** 流程id */
    @Column(length = 64)
    @ColumnComment("流程id")
    private String flowid;

    /** 流程名称 */
    @Column(length = 100)
    @ColumnComment("流程名称")
    private String flowname;

    /** 流程标题 */
    @Column(length = 1000)
    @ColumnComment("流程标题")
    private String flowtitle;

    /** 流程发起人ID */
    @Column(length = 64)
    @ColumnComment("流程发起人ID")
    private String promoterid;

    /** 流程发起时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ColumnComment("流程发起时间")
    private Date starttime;

    /** 流程结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ColumnComment("流程结束时间")
    private Date endtime;
}
