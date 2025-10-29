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
@Table(name = "sys_dept")
@TableComment("部门表")
public class SysDept extends BasePO {

    @Column(length = 50)
    @ColumnComment("部门名称")
    private String name;

    @Column(length = 20)
    @ColumnComment("负责人")
    private String leader;

    @Column(length = 20)
    @ColumnComment("联系方式")
    private String phone;

    @Column(length = 50)
    @ColumnComment("邮箱")
    private String email;

    @Column(length = 2000)
    @ColumnComment("祖级列表")
    private String ancestors;

    @Column(length = 64)
    @ColumnComment("父级id")
    private String parentId;

    @ColumnComment("显示顺序")
    private Integer orderNum;

    @Column(columnDefinition = "int DEFAULT 0")
    @ColumnComment("类型: 1-职能部门单位; 2-区划;")
    private Integer type;

    @Column(columnDefinition = "int DEFAULT 0")
    @ColumnComment("部门状态（0正常 1停用）")
    private Integer status;

    @Column(length = 200)
    @ColumnComment("绑定政务微信部门id-用于和事件对接分派单位id")
    private String wechatDeptId;
}
