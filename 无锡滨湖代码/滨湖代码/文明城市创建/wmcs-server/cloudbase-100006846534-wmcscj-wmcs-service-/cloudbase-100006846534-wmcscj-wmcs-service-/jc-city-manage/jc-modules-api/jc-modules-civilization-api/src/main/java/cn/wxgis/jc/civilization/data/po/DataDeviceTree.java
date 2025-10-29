package cn.wxgis.jc.civilization.data.po;

import cn.wxgis.jc.jpa.domain.comment.ColumnComment;
import cn.wxgis.jc.jpa.domain.comment.TableComment;
import cn.wxgis.jc.jpa.domain.po.BasePO;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "data_device_tree")
@TableComment("视频设备组织表")
public class DataDeviceTree extends BasePO {

    /** 节点类型。1:组织 */
    @Column(columnDefinition = "int DEFAULT 1")
    @ColumnComment("节点类型。1:组织")
    private Integer nodeType;

    /** 组织名称 */
    @Column(length = 200)
    @ColumnComment("组织名称")
    private String name;

    /** 界面显示的图标名称 */
    @Column(length = 200)
    @ColumnComment("界面显示的图标名称")
    private String icon;

    /** 组织类型，"1"为基本组织 */
    @Column(length = 50)
    @ColumnComment("组织类型，1为基本组织")
    private String orgType;

    /** 是否是父节点（是否有子节点），true:是父节点，false:不是父节点 */
    @ColumnComment("是否是父节点（是否有子节点），true:是父节点，false:不是父节点")
    private boolean isParent;

    /** 父节点ID，如果父节点是root节点则为"" */
    @Column(length = 64)
    @ColumnComment("父节点ID，如果父节点是root节点则为''")
    private String parentId;

    /** 父节点ID，如果父节点是root节点则为''*/
    @Column(length = 64)
    @ColumnComment("父节点ID，如果父节点是root节点则为''")
    private String orgCode;

    /** 排序码 */
    @ColumnComment("排序码")
    private Integer sort;


    /** 组织自定义编码，通常用于国标编码'' */
    @Column(length = 64)
    @ColumnComment("组织自定义编码，通常用于国标编码''")
    private String orgSn;

    /** 和orgSn一样 */
    @Column(length = 64)
    @ColumnComment("和orgSn一样")
    private String sn;

    /** 级联域Id，字段为空或者0表示本级 */
    @ColumnComment("级联域Id，字段为空或者0表示本级")
    private Long domainId;


    @Column(length = 64)
    @ColumnComment("区划id")
    private String regionCode;

    @Column(length = 100)
    @ColumnComment("区划全称")
    private String regionAllName;

}
