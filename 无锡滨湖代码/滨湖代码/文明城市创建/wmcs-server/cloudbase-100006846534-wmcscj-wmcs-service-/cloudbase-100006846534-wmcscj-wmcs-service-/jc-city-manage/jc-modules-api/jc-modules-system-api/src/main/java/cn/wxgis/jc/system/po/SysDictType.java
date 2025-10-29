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
@Table(name = "sys_dict_type")
@TableComment("字典类型表")
public class SysDictType extends BasePO {

    @Column(length = 100)
    @ColumnComment("字典名称")
    private String dictName;

    @Column(length = 100)
    @ColumnComment("字典类型")
    private String dictType;

    @Column(columnDefinition = "int DEFAULT 0")
    @ColumnComment("字典类型状态：0启用；1不启用")
    private Integer status;
}
