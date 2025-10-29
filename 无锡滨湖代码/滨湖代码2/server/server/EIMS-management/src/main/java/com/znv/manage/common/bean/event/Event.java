package com.znv.manage.common.bean.event;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * (TCfgEvent)实体类
 *
 * @author makejava
 * @since 2022-12-09 13:41:36
 */
@Data
@ApiModel(description= "事件实体类")
public class Event implements Serializable {
    private static final long serialVersionUID = -84222738767908607L;

    private Integer id;

    @ApiModelProperty(value = "事件标题")
    private String eventTitle;

    @ApiModelProperty(value = "事故类型ID 对应dict表")
    private Integer typeId;

    @ApiModelProperty(value = "事故类型名称")
    private String typeName;

    @ApiModelProperty(value = "街道ID")
    private String streetId;

    @ApiModelProperty(value = "街道名称")
    private String streetName;

    @ApiModelProperty(value = "社区ID")
    private String communityId;

    @ApiModelProperty(value = "社区名称")
    private String communityName;

    @ApiModelProperty(value = "事件级别")
    private String levelName;

    @ApiModelProperty(value = "事件级别id")
    private String level;

    @ApiModelProperty(value = "事件时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date eventTime;

    @ApiModelProperty(value = "小类名称ID 对应dict表")
    private Integer smallTypeId;

    @ApiModelProperty(value = "小类名称")
    private String smallTypeName;

    @ApiModelProperty(value = "影响级别")
    private String impactLevel;

    @ApiModelProperty(value = "设备列表")
    private String device_ids;

    @ApiModelProperty(value = "监督单位")
    private String monitorUnit;

    @ApiModelProperty(value = "事发地点")
    private String occurAddress;

    @ApiModelProperty(value = "轻伤人员")
    private Integer minorNum;

    @ApiModelProperty(value = "重伤人员")
    private Integer seriousNum;

    @ApiModelProperty(value = "死亡人员")
    private Integer deathNum;

    @ApiModelProperty(value = "经济损失")
    private String economicLoss;

    @ApiModelProperty(value = "是否总结")
    private String isConclusion;

    @ApiModelProperty(value = "概述")
    private String summary;

    @ApiModelProperty(value = "来源名称")
    private String sourceName;

    @ApiModelProperty(value = "来源id")
    private String sourceId;

    @ApiModelProperty(value = "是否指挥")
    private String isCommand;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty(value = "节点列表")
    private List<EventNodeContact> eventNodeList;

    @ApiModelProperty(value = "经度")
    private String lon;

    @ApiModelProperty(value = "纬度")
    private String lat;

    @ApiModelProperty(value = "文件地址-视频")
    private String fileVideo;

    @ApiModelProperty(value = "文件地址-图片")
    private String filePicture;

    @ApiModelProperty(value = "文件地址-音频")
    private String fileVoice;

    @ApiModelProperty(value = "事故总结")
    private String conclusion;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "状态名称")
    private String statusName;

    @ApiModelProperty(value = "预案实体id")
    private Integer eventPlanId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "跨度开始时间")
    private Date spanStartTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "跨度开始时间")
    private Date spanEndTime;

}
