package com.znv.manage.common.bean.resource;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class HistoricalEvent {
    /**
     * 主键
     */
    private Long id;

    /**
     * 事故标题
     */
    private String accidentTitle;

    /**
     * 事故地点
     */
    private String accidentAddress;

    /**
     * 事故案例类型
     */
    private String accidentCasesType;

    /**
     * 事故类型
     */
    private String accidentType;

    /**
     * 发生日期
     */
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date occurrenceDate;

    /**
     * 监管行业
     */
    private String regulatoryIndustry;

    /**
     * 事故模型
     */
    private String accidentModel;

    /**
     * 轻伤人数
     */
    private Integer minorNum;

    /**
     * 重伤人数
     */
    private Integer seriousNum;

    /**
     * 死亡人数
     */
    private Integer deathNum;

    /**
     * 经济损失
     */
    private Integer economicLoss;

    /**
     * 事故原因
     */
    private String accidentCause;

    /**
     * 预防措施
     */
    private String preventiveMeasure;

    /**
     * 附件
     */
    private String annexUrl;
}
