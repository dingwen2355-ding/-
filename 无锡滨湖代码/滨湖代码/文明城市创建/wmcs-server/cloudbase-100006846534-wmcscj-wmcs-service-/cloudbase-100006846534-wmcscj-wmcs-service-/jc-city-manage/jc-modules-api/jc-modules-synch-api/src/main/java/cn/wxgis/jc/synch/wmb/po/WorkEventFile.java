package cn.wxgis.jc.synch.wmb.po;

import cn.wxgis.jc.jpa.domain.comment.TableComment;
import cn.wxgis.jc.jpa.domain.po.BasePO;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "work_event_file")
@TableComment("原历史事件")
public class WorkEventFile extends BasePO {

    @Column(length = 100)
    private String code;

    @Column(length = 100)
    private String fileType;

    @Column(length = 100)
    private String name;

    @Column(length = 100)
    private String ossName;

    @Column(length = 2000)
    private String uriBig;

    @Column(length = 2000)
    private String url;

    @Column(length = 100)
    private String eventId;

    @Column(length = 2000)
    private String file;
}
