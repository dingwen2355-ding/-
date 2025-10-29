package cn.wxgis.jc.civilization.data.po;

import cn.wxgis.jc.jpa.domain.comment.ColumnComment;
import cn.wxgis.jc.jpa.domain.comment.TableComment;
import cn.wxgis.jc.jpa.domain.po.BasePO;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "data_check_item")
@TableComment("点位检查项")
public class DataCheckItem extends BasePO {

    /** 检查项 */
    @Column(length = 300)
    @ColumnComment("检查项")
    private String name;

    /** 负责单位 */
    @Column(length = 2000)
    @ColumnComment("负责单位")
    private String deptIds;

    @Column(length = 2000)
    @ColumnComment("负责单位名称")
    private String deptNames;

    /** 分数 */
    @Column(columnDefinition = "decimal(19,6)")
    @ColumnComment("分数")
    private BigDecimal score;

    /** 排序 */
    @ColumnComment("排序")
    private Integer orderNum;

    /** 点位类型id */
    @Column(length = 64)
    @ColumnComment("点位类型id")
    private String typeId;

    /** 事件类型id */
    @Column(length = 64)
    @ColumnComment("事件类型id")
    private String eventTypeId;
}
