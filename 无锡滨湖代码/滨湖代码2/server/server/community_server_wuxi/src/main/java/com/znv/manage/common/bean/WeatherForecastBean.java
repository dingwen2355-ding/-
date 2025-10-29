package com.znv.manage.common.bean;

import lombok.Data;

@Data
public class WeatherForecastBean {

    private String id;
    private String t;
    private String weatherCode;
    private String weatherName;
    private String ws;
    private String wd;
    private String updateTime;

}
