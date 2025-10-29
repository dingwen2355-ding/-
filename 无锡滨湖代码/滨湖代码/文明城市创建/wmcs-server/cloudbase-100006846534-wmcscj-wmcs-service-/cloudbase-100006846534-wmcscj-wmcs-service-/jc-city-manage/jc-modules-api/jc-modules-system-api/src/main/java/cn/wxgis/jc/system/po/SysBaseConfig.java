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
@Table(name = "sys_base_config")
@TableComment("系统配置值")
public class SysBaseConfig extends BasePO {

    @Column(length = 100)
    @ColumnComment("系统名称")
    private String name;

    @Column(columnDefinition = "int DEFAULT 1")
    @ColumnComment("是否自动派发")
    private Integer autoGive;

    @ColumnComment("处置时限")
    private Integer limitTime;

    @Column(length = 100)
    @ColumnComment("处置时限单位")
    private String limitTimeUnit;

    @Column(length = 64)
    @ColumnComment("审核部门id")
    private String examineDeptId;

}
