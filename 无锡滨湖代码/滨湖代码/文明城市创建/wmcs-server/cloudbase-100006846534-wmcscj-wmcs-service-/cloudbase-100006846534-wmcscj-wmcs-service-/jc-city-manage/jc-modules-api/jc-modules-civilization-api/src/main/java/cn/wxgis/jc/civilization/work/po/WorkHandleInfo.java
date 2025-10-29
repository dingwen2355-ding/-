package cn.wxgis.jc.civilization.work.po;

import cn.wxgis.jc.jpa.domain.comment.ColumnComment;
import cn.wxgis.jc.jpa.domain.comment.TableComment;
import cn.wxgis.jc.jpa.domain.po.BasePO;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "work_handle_info")
@TableComment("工单处理存储信息（用于计算告警查看开始时间）")
public class WorkHandleInfo extends BasePO {

    @Column(length = 20)
    @ColumnComment("AI告警类型")
    private String type;

    @Column(length = 20)
    @ColumnComment("日期")
    private String date;

    @Column(length = 20)
    @ColumnComment("时间")
    private String time;

    @Column(length = 20)
    @ColumnComment("处理时间")
    private Date handleTime;
}
