package cn.wxgis.jc.civilization.work.po;

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
@Table(name = "work_appearance")
@TableComment("事件现象")
public class WorkAppearance extends BasePO {

    /** 上报人 */
    @Column(length = 100)
    @ColumnComment("上报人")
    private String submitUser;

    /** 上报时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ColumnComment("上报时间")
    private Date submitTime;

    /** 问题标题 */
    @Column(length = 100)
    @ColumnComment("问题标题")
    private String title;

    /** 问题描述 */
    @Column(length = 2000)
    @ColumnComment("问题描述")
    private String info;

    /** 事发地 */
    @Column(length = 200)
    @ColumnComment("事发地")
    private String address;

    /** 是否创建工单：0-否；1-是 */
    @Column(columnDefinition = "int DEFAULT 0")
    @ColumnComment("是否创建工单：0-否；1-是")
    private String createEventFlag;

    /** 事件编号 */
    @Column(length = 100)
    @ColumnComment("事件编号")
    private String workCode;

    /** 所属区划 */
    @Column(length = 64)
    @ColumnComment("所属区划")
    private String regionCode;

    /** 所属区 */
    @Column(length = 64)
    @ColumnComment("所属区")
    private String city;

    /** 所属街道 */
    @Column(length = 64)
    @ColumnComment("所属街道")
    private String town;

    /** 所属社区 */
    @Column(length = 64)
    @ColumnComment("所属社区")
    private String community;

    /** 附件 */
    @Column(length = 2000)
    @ColumnComment("附件")
    private String file;

    /** 状态 */
    @Column(columnDefinition = "int DEFAULT 0")
    @ColumnComment("状态")
    private Integer status;

}
