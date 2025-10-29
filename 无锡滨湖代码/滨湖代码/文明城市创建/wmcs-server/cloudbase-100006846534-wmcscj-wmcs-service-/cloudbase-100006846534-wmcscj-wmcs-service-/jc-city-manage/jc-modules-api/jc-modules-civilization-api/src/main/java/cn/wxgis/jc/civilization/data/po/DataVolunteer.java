package cn.wxgis.jc.civilization.data.po;

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
@Table(name = "data_volunteer")
@TableComment("志愿者")
public class DataVolunteer extends BasePO {

    /** 所属区划 */
    @Column(length = 64)
    @ColumnComment("所属区划")
    private String regionCode;

    /** 真实姓名 */
    @Column(length = 100)
    @ColumnComment("真实姓名")
    private String name;

    /** 身份证信息 */
    @Column(length = 100)
    @ColumnComment("身份证信息")
    private String cardnumber;

    /** 性别 */
    @Column(length = 20)
    @ColumnComment("性别")
    private Integer sex;

    /** 学历 */
    @Column(length = 20)
    @ColumnComment("学历")
    private String education;

    /** 职业 */
    @Column(length = 50)
    @ColumnComment("职业")
    private String position;

    /** 单位名称 */
    @Column(length = 100)
    @ColumnComment("单位名称")
    private String corporateName;

    /** 详细住址 */
    @Column(length = 200)
    @ColumnComment("详细住址")
    private String address;

    /** 邮编 */
    @Column(length = 50)
    @ColumnComment("邮编")
    private String zipCode;

    /** 邮箱 */
    @Column(length = 100)
    @ColumnComment("邮编")
    private String email;

    /** 固话 */
    @Column(length = 20)
    @ColumnComment("固话")
    private String tel;

    /** 联系电话 */
    @Column(length = 20)
    @ColumnComment("联系电话")
    private String phone;

    /** 工作简历 */
    @Column(length = 200)
    @ColumnComment("工作简历")
    private String jobResume;

    /** 申请日期 */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ColumnComment("申请日期")
    private Date applyDate;

    /** 用户头像 */
    @ColumnComment("用户头像")
    @Column(length = 1000)
    private String avatar;

    /** 年龄 */
    @ColumnComment("年龄")
    private Integer age;

    /** 志愿者类型 */
    @Column(length = 20)
    @ColumnComment("志愿者类型")
    private String type;


    /** 志愿团队id */
    @ColumnComment("志愿团队id")
    private String teamId;

    /** 状态 */
    @Column(columnDefinition = "int DEFAULT 0")
    @ColumnComment("状态")
    private Integer status;

}
