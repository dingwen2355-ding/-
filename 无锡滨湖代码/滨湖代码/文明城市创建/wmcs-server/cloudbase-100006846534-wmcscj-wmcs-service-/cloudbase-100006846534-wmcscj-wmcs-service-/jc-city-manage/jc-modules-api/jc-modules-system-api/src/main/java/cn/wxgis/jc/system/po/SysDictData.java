package cn.wxgis.jc.system.po;

import cn.wxgis.jc.jpa.domain.comment.ColumnComment;
import cn.wxgis.jc.jpa.domain.comment.TableComment;
import cn.wxgis.jc.jpa.domain.po.BasePO;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "sys_dict_data")
@TableComment("字典数据表")
public class SysDictData extends BasePO {

    @Column(length = 100)
    @ColumnComment("字典类型")
    private String dictType;

    @Column(length = 100)
    @ColumnComment("字典标签")
    private String dictLabel;

    @Column(length = 100)
    @ColumnComment("字典值")
    private String dictValue;

    /** 排序 */
    @ColumnComment("排序")
    private Integer orderNum;

    @Column(columnDefinition = "int DEFAULT 0")
    @ColumnComment("是否默认：0否；1是")
    private Integer defaultFlag;

    @Column(columnDefinition = "int DEFAULT 0")
    @ColumnComment("字典数据状态：0启用；1不启用")
    private Integer status;

}
