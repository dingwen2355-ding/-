package com.znv.manage.common.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Wgygjxx {
    private Integer uid;
    private String uname;
    private Float posy;
    private Float posx;
    private Float oriposy;
    private Float oriposx;
    private String occurtime;
    private String storagetime;

}
