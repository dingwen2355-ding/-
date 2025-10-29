package cn.wxgis.jc.civilization.work.po;

import cn.wxgis.jc.jpa.domain.comment.ColumnComment;
import cn.wxgis.jc.jpa.domain.comment.TableComment;
import cn.wxgis.jc.jpa.domain.po.BasePO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "work_threshold")
@TableComment("阈值管理表")
public class WorkThreshold extends BasePO {

    /** AI告警类型 */
    @Column(length = 64)
    @ColumnComment("AI告警类型")
    private String alarmType;

    /** AI告警类型 */
    @Column(length = 64)
    @ColumnComment("对应事件类型")
    private String eventType;

    @ColumnComment("正常日常告警到达次数")
    private String normalOverCount;

    @ColumnComment("检测类型：1-分；2-时；3-天")
    private Integer type;

    @ColumnComment("是否开启特殊模式")
    private Integer specialFlag;

    @Column(length = 64)
    @ColumnComment("特殊模式时间段配置")
    private String timeConfigId;

    @ColumnComment("达到告警次数")
    private String overCount;

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ColumnComment("开始时间")
    private String startTime;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ColumnComment("结束时间")
    private String finishTime;
}
