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
@Table(name = "sys_region")
@TableComment("区划表")
public class SysRegion extends BasePO {

    /**
     * 区域编码
     */
    @Column(length = 50)
    @ColumnComment("区划编码")
    private String regionCode;

    /**
     * 区域名称
     */
    @Column(length = 100)
    @ColumnComment("区划名称")
    private String regionName;

    /**
     * 区域概况
     */
    @Column(length = 500)
    @ColumnComment("区域概况")
    private String areaSituation;

    /**
     * 父级id
     */
    @ColumnComment("父级id")
    private String parentId;

    /**
     * 区域类型 基础网格，专属网格
     */
    @ColumnComment( "区域类型 0-基础网格，1-9-专属网格 1-行政中心；2-各类园区；3-商务楼宇；4-商圈市场；5-学校；6-农林场；7-医疗卫生机构；8-企事业单位；9-其他")
    private Integer regionType;

    /**
     * 地图初始化级别
     */
    @ColumnComment("地图初始化级别")
    private Integer mapLevel;

    /**
     * 区域级别
     */
    @ColumnComment("区域级别")
    private Integer level;

    /** 排序 */
    @ColumnComment("排序")
    @Column(columnDefinition = "int DEFAULT 0")
    private Integer orderNum;

    /** 是否县级市 1是0否
     * 定义如下：
     * 在跨了区县级别（6位code不相等的情况下）
     * 比如梁溪区-新吴区都是0
     * 宜兴县，江阴算1
     * 只有0-0才可以录第二条户籍
     * 0-1（原新吴区录了户籍，再录到宜兴）
     * 1-1（原宜兴录了户籍--再录到--江阴）
     * 1-0（原宜兴录了户籍，再录到新吴区）*/
    @ColumnComment("是否县级市")
    private Integer countyFlag;

    @Column(length = 64)
    @ColumnComment("部门id")
    private String deptId;

}
