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
@Table(name = "sys_role")
@TableComment("角色表")
public class SysRole extends BasePO {

    @Column(length = 30)
    @ColumnComment("角色名称")
    private String name;

    @Column(length = 100)
    @ColumnComment("角色权限字符串")
    private String code;

    @ColumnComment("排序")
    private Integer orderNum;

    @Column(length = 10)
    @ColumnComment("数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）")
    private String dataScope;

    @Column(columnDefinition = "int DEFAULT 0")
    @ColumnComment("状态")
    private Integer status;

}
