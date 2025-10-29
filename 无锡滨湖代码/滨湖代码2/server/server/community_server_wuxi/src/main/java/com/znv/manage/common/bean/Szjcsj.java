package com.znv.manage.common.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Title: szjcsj
 * @Description:水质数据监测实体类
 * @Author: 李贝德
 * @Date: 2021/7/1 16:42
 **/
@Data
public class Szjcsj {

    private Date collectTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date gatherTime;
    private Date createTime;

    private String SPAD;
    private String siteName;
    private String AMMONIA_NITROGEN;
    private String TOTAL_NITROGEN;
    private String COMN;
    private String DISSOLVED_OXYGEN;
    private String CONDUCTIVITY;
    private String CYANOBACTERIA;
    private String PH;
    private String TURBIDITY;
    private String TOTAL_PHOSPHORUS;

}
