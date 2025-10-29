package cn.wxgis.jc.synch.unify.po;

import cn.wxgis.jc.jpa.domain.comment.ColumnComment;
import cn.wxgis.jc.jpa.domain.comment.TableComment;
import cn.wxgis.jc.jpa.domain.po.BasePO;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "unify_dept")
@TableComment("第三方同步组织结构")
public class UnifyDept extends BasePO {

    /** 部门名称 */
    @Column(length = 100)
    @ColumnComment("部门名称")
    private String name;

    /** 父部门id。根部门为1 */
    @Column(length = 64)
    @ColumnComment("父部门id。根部门为1")
    private String parentId;

    /** 在父部门中的次序值 */
    @ColumnComment("在父部门中的次序值")
    private Integer sort;

    /** 部门类型的标记。0表示普通部门，1表示实体单位；2表示虚拟单位，3表示行政区划。未赋值时，默认是普通部门 */
    @ColumnComment("部门类型的标记。0表示普通部门，1表示实体单位；2表示虚拟单位，3表示行政区划。未赋值时，默认是普通部门")
    private Integer type;

    /** 部门可添加成员上限编号 */
    @ColumnComment("部门可添加成员上限编号")
    private Integer partyUserLimit;

    /** 单位全称 */
    @ColumnComment("单位全称")
    private String fullName;

    /** 单位简称 */
    @ColumnComment("单位简称")
    private String shortName;

    /** 单位简介 */
    @ColumnComment("单位简介")
    private String briefIntroduction;

    /** 单位网站 */
    @ColumnComment("单位网站")
    private String domainName;

    /** 单位地址 */
    @ColumnComment("单位地址")
    private String address;

    /** 单位电话 */
    @ColumnComment("单位电话")
    private String telephone;

    /** 社会信用代码 */
    @ColumnComment("社会信用代码")
    private String organizationCode;

}

