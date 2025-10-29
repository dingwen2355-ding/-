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
@Table(name = "sys_menu")
@TableComment("角色表")
public class SysMenu extends BasePO {

    @Column(length = 50)
    @ColumnComment("菜单名称")
    private String name;

    @Column(length = 64)
    @ColumnComment("父级id")
    private String parentId;

    @Column(length = 200)
    @ColumnComment("路由地址")
    private String path;

    @Column(length = 200)
    @ColumnComment("路由参数")
    private String query;

    @Column(length = 200)
    @ColumnComment("组件链接")
    private String component;

    @Column(length = 200)
    @ColumnComment("菜单地址：全路径地址")
    private String link;

    @Column(length = 200)
    @ColumnComment("权限标识")
    private String perms;

    @Column(length = 500)
    @ColumnComment("图标")
    private String icon;

    @Column(columnDefinition = "int DEFAULT 1")
    @ColumnComment("平台：1PC；2APP")
    private Integer platform;

    @ColumnComment("菜单类型（M目录 C菜单 F按钮）")
    private String type;

    @ColumnComment("显示顺序")
    private Integer orderNum;

    @Column(columnDefinition = "int DEFAULT 0")
    @ColumnComment("是否外链")
    private Integer frameFlag;

    @ColumnComment("菜单状态（0显示 1隐藏）")
    private Integer visible;

    @ColumnComment("菜单状态（0正常 1停用）")
    private Integer status;

}
