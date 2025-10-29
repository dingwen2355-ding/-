package com.znv.manage.common.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Wgyxx {
    private Integer id;
    private String reseau;
    private String nation;
    private String street;
    private String name;
    private String tel;
    private String reseauindex;
    private String community;
    private String collecttime;
    private String storagetime;
}
