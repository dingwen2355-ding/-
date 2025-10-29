package com.znv.manage.common.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Title: Dqhjxssjb
 * @Description:
 * @Author: 李贝德
 * @Date: 2021/7/8 9:30
 **/
@Data
public class Dqhjxssjb {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date starttime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date endtime;

    private String id;
    private String maxaqiitemname;
    private String aqilevelstep;
    private String maxaqiitem;
    private String so2Value;
    private String stationname;

    private Double pm25Value;
    private Double no2Value;
    private Double pm25Aqi;
    private Double no2Aqi;
    private Double so2Aqi ;
    private Double temperature;
    private Double humidity;
    private Double pm10Value;
    private Double coAqi;
    private Double aqi;
    private Double o3Value;
    private Double pressure;
    private Double coValue;
    private Double winddirection;
    private Double pm10Aqi;
    private Double windspeed;

}
