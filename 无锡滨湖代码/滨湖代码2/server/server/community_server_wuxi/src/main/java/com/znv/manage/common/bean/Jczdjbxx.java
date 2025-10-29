package com.znv.manage.common.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Jczdjbxx {
    private String stcd;
    private String stnm;
    private String street;
    private Float lgtd;
    private Float lttd;
    private String sttp;
    private String category;
    private String storagetime;
}
