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
@Table(name = "unify_user")
@TableComment("第三方用户数据")
public class UnifyUser extends BasePO {

    /** 账号 */
    @Column(length = 100)
    @ColumnComment("账号")
    private String account;

    /** 用户名 */
    @Column(length = 100)
    @ColumnComment("用户名")
    private String name;

    /** 主岗部门 */
    @Column(length = 64)
    @ColumnComment("主岗部门")
    private String majorDept;

    /** 主岗部门职位信息 */
    @Column(length = 200)
    @ColumnComment("主岗部门职位信息")
    private String majorPosition;

    /** 手机号码 */
    @Column(length = 50)
    @ColumnComment("手机号码")
    private String majorMobile;

    /** 性别 */
    @ColumnComment("性别")
    private Integer gender;

    /** 别名 */
    @Column(length = 100)
    @ColumnComment("别名")
    private String englishName;

    /** 邮箱 */
    @Column(length = 100)
    @ColumnComment("邮箱")
    private String email;

    /** 其他部门id */
    @Column(length = 1000)
    @ColumnComment("其他部门id")
    private String otherDept;

    /** 其他部门id */
    @Column(length = 2000)
    @ColumnComment("其他部门id")
    private String otherPosition;

}
