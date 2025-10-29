package com.znv.manage.common.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Sw {
    private Float q;
    private String stcd;
    private Float z;
    private String tm;
    private String storagetime;
}
