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
@Table(name = "wf_event_message")
@TableComment("事件消息表")
public class WfEventMessage extends BasePO {

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ColumnComment("发送时间")
    private Date sendTime;

    @Column(length = 64)
    @ColumnComment("发送人")
    private String sendUserId;

    @Column(length = 64)
    @ColumnComment("接收人")
    private String reviceUserId;

    @Column(length = 2000)
    @ColumnComment("消息内容")
    private String content;

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
    @ColumnComment("是否超时：0否；1是")
    private String readFlag;


}
