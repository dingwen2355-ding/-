package cn.wxgis.jc.system.po;

import cn.wxgis.jc.jpa.domain.comment.ColumnComment;
import cn.wxgis.jc.jpa.domain.comment.TableComment;
import cn.wxgis.jc.jpa.domain.po.BasePO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "sys_user")
@TableComment("用户表")
public class SysUser extends BasePO {

    @Column(length = 30)
    @ColumnComment("用户账号")
    private String userName;

    @ColumnComment("登录密码")
    @Column(length = 100)
    private String password;

    @Column(length = 30)
    @ColumnComment("用户名称")
    private String nickName;

    @Column(length = 64)
    @ColumnComment("归属部门id")
    private String deptId;

    @Column(length = 200)
    @ColumnComment("职位")
    private String position;

    @Column(length = 50)
    @ColumnComment("邮箱")
    private String email;

    @Column(length = 20)
    @ColumnComment("手机号")
    private String phonenumber;

    @Column(length = 50)
    @ColumnComment("生日")
    private String birthday;

    @ColumnComment("用户头像")
    @Column(length = 1000)
    private String avatar;

    @Column(length = 50)
    @ColumnComment("最后登陆IP")
    private String loginIp;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ColumnComment("最后登陆时间")
    private Date loginDate;

    @ColumnComment("用户性别")
    @Column(columnDefinition = "int DEFAULT 0")
    private Integer sex;

    @Column(columnDefinition = "int DEFAULT 0")
    @ColumnComment("用户状态")
    private Integer status;

}
