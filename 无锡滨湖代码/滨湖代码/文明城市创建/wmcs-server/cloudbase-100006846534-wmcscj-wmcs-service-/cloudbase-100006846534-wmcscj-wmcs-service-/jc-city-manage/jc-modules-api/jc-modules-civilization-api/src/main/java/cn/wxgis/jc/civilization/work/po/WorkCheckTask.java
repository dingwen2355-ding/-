package cn.wxgis.jc.civilization.work.po;

import cn.wxgis.jc.jpa.domain.comment.ColumnComment;
import cn.wxgis.jc.jpa.domain.comment.TableComment;
import cn.wxgis.jc.jpa.domain.po.BasePO;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "work_check_task")
@TableComment("巡查任务")
public class WorkCheckTask extends BasePO {

    @ColumnComment("任务类型：1-常规类型；2-特殊类型")
    private Integer type;

    @Column(length = 64)
    @ColumnComment("点位类型id")
    private String pointsTypeId;

    @Column(length = 100)
    @ColumnComment("点位类型名称")
    private String pointsTypeName;

    @Column(length = 64)
    @ColumnComment("巡查点位id")
    private String pointsId;

    @Column(length = 100)
    @ColumnComment("巡查点位名称")
    private String pointsName;

    @Column(length = 500)
    @ColumnComment("巡查内容")
    private String content;

    @Column(length = 64)
    @ColumnComment("检查项id")
    private String checkItemId;

    @Column(length = 500)
    @ColumnComment("检查项")
    private String checkItem;

    /** 状态：0-待巡查、1-合格、2-不合格已处理、3-不合格处理中、4-不合格待处理 */
    @Column(columnDefinition = "int DEFAULT 0")
    @ColumnComment("状态：0-待巡查、1-合格、2-不合格已处理、3-不合格处理中、4-不合格待处理")
    private String status;
}
