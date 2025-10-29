package com.znv.manage.common.bean.plan;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 突发事件表 t_platform_emergency
 *
 * @author yangbo
 * @email
 * @date 2021-12-06 15:53:12
 */
@Data
//@TableName("t_platform_emergency")
public class PlatformEmergencyEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 事件Id
     */
    @ApiModelProperty(value = "事件Id")
    private Integer id;
    /**
     * 事件标题
     */
    @ApiModelProperty(value = "事件标题")
    private String eventTitle;
    /**
     * 事件一级类型
     */
    @ApiModelProperty(value = "事件一级类型")
    private String firstType;
    /**
     * 事件二级类型
     */
    @ApiModelProperty(value = "事件二级类型")
    private String secondType;
    /**
     * 事件发生时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "事件发生时间")
    private Date occurrenceTime;
    /**
     * 事件发生地点
     */
    @ApiModelProperty(value = "事件发生地点")
    private String occurrenceAddress;
    /**
     * 经度
     */
    @ApiModelProperty(value = "经度")
    private String longitude;
    /**
     * 纬度
     */
    @ApiModelProperty(value = "纬度")
    private String latitude;
    /**
     * 事件描述
     */
    @ApiModelProperty(value = "事件描述")
    private String eventDescription;
    /**
     * 上报人
     */
    @ApiModelProperty(value = "上报人")
    private String reportPeople;
    /**
     * 联系方式
     */
    @ApiModelProperty(value = "联系方式")
    private String contactInformation;
    /**
     * 附件路径
     */
    @ApiModelProperty(value = "附件路径")
    private String attachmentPath;
    /**
     * 事件状态(1:初报/2:续报/3:终报)
     */
    @ApiModelProperty(value = "事件状态(1:初报/2:续报/3:终报)")
    private String eventState;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

}
