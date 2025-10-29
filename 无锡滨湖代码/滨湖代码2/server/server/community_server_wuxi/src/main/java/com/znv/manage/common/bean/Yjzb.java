package com.znv.manage.common.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Title: Yjzb
 * @Description:预警总表实体类
 * @Author: 李贝德
 * @Date: 2021/7/1 10:17
 **/
@Data
public class Yjzb {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date beginTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date publishTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date reportTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date responseStartTime;
    private Date createTime;


    private Double state;

    private Double datumValue;
    private Double lttd;
    private Double lgtd;

    private String id;
    private String preStartRespUser;
    private String pfmid;
    private String remark;
    private String ender;
    private String source;
    private String title;
    private String reponseLevel;
    private String forecastLevel;
    private String responseDepartment;
    private String street;
    private String scope;
    private String place;
    private String responseCharger;
    private String reponseMeasure;
    private String summary;
    private String summaryer;
    private String sendUsers;
    private String imgListStr;
    private String author;
    private String sendtype;
    private String stcd;
    private String publisher;
    private String reponser;
}
