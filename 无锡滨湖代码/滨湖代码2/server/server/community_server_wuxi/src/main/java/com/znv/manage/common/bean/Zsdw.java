package com.znv.manage.common.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Zsdw {
    private String id;
    private String indexid;
    private String districtid;
    private Float dataminutes;
    private String siteattrid;
    private String functiontype;
    private String sitename;
    private String userid;
    private Float orderby;
    private Float lat;
    private Float lng;
    private Integer delflag;
    private Integer opendata;
    private String street;
    private String createtime;
    private String updatetime;
    private String storagetime;
}
