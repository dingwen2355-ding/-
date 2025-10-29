package com.znv.manage.common.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Title: spAIgjxx
 * @Description:视频AI告警信息实体类
 * @Author: 李贝德
 * @Date: 2021/7/2 15:41
 **/
@Data
public class SpAIgjxx {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date time;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date collectTime;
    private Date createTime;

    private Integer processStatus;
    private Integer judgeStatus;
    private Integer eventType;
    private Integer duration;
    private Integer eventLevel;
    private Integer continuing;

    private String cameraDistrict;
    private String cameraType;
    private String cameraDeviceCode;
    private String url;
    private String quality;
    private String cameraLongitude;
    private String cameraLatitude;
    private String appId;
    private String alarmId;
    private String camera;
    private String cameraSerial;
    private String cameraName;
    private String extraInfo;
    private String sectionLocations;
}
