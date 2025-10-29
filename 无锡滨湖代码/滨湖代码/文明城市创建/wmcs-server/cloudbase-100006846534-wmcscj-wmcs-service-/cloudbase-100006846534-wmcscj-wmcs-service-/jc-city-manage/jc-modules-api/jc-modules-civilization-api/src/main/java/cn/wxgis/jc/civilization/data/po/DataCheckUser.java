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
@Table(name = "data_check_user")
@TableComment("督察员")
public class DataCheckUser extends BasePO {

    /** 绑定用户 */
    private String userId;

    /** 所属区划 */
    @Column(length = 64)
    @ColumnComment("所属区划")
    private String regionCode;

    /** 所属部门 */
    @Column(length = 64)
    @ColumnComment("所属部门")
    private String deptId;

    /** 督察员名称 */
    @Column(length = 100)
    @ColumnComment("督察员名称")
    private String name;

    /** 身份证 */
    @Column(length = 100)
    @ColumnComment("身份证")
    private String cardnumber;

    /** 联系方式 */
    @Column(length = 20)
    @ColumnComment("联系方式")
    private String phone;

    /** 联系住址 */
    @Column(length = 100)
    @ColumnComment("联系方式")
    private String address;

    /** 关联点位id */
    @Column(length = 2000)
    @ColumnComment("关联点位id")
    private String pointsId;

    /** 督察员类型 */
    @Column(length = 20)
    @ColumnComment("督察员类型")
    private String type;

    /** 状态：0启用；1停用 */
    @Column(columnDefinition = "int DEFAULT 0")
    @ColumnComment("状态：0启用；1停用")
    private Integer status;

}
