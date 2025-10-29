package com.znv.manage.common.bean.resource;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class Risk {
    /**
     * 主键
     */
    private Long id;

    /**
     * 行业
     */
    private String industry;

    /**
     * 企业名称
     */
    private String enterpriseName;

    /**
     * 发现日期
     */
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date discoveryDate;

    /**
     * 隐患类型
     */
    private String dangersType;

    /**
     * 隐患来源
     */
    private String dangersSources;

    /**
     * 隐患明细
     */
    private String dangersDetails;

    /**
     * 整改措施
     */
    private String rectificationMeasures;

    /**
     * 整改时限
     */
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date rectificationDate;

    /**
     * 监管责任部门
     */
    private String regulatoryDepartment;

    /**
     * 整改监管责任人
     */
    private String responsiblePerson;

    /**
     * 整改监管责任人联系方式
     */
    private String responsiblePersonPhone;

    /**
     * 整改进度
     */
    private String rectificationProgress;

    /**
     * 是否立案
     */
    private String isCase;

    /**
     * 是否约谈
     */
    private String isAppointment;

    /**
     * 罚款
     */
    private String isFine;

    /**
     * 是否曝光
     */
    private String isExposure;

    /**
     * 是否停产整顿
     */
    private String isRectification;

    /**
     * 是否关闭取缔
     */
    private String isClose;

    /**
     * 是否移送司法机关追究刑事责任
     */
    private String isTransfer;
}
