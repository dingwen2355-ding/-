package com.znv.manage.common.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class MzJzGh {
    private String personname;

    private String idtype;

    private String idno;

    private String idcard;

    private String visitdate;

    private String visittime;

    private String ghsj;

    private String organname;

    private String storagetime;
}
