package com.znv.manage.common.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class Jqsskll {
    private String areaname;
    private Integer city;
    private Integer incity;
    private Integer outcity;
    private Integer areaid;
    private Integer cumulativeoutcity;
    private Integer inprovince;
    private Integer cumulativeincity;
    private Integer total;
    private Integer cumulativeoutprovince;
    private String time;
    private String collecttime;
    private Integer outprovince;
    private Integer cumulativetotal;
    private String storagetime;
}
