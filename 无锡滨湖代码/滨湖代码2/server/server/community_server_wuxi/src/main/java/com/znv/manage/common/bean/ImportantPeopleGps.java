package com.znv.manage.common.bean;


import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.util.StringUtils;

@Data
public class ImportantPeopleGps {

    private String id;
    private String name;
    private String cardId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private String startTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private String endTime;
    private String transportation;
    private String startAddress;
    private String endAddress;
    private String startAddressDetail;
    private String endAddressDetail;
    private String startGpsx;
    private String startGpsy;
    private String endGpsx;
    private String endGpsy;

    public String checkInfo() {
        if(StringUtils.isEmpty(name)){
            return "姓名为空!";
        }
        if(StringUtils.isEmpty(startTime) && StringUtils.isEmpty(endTime)){
            return "出发时间和到达时间不可同时为空!";
        }
        if(StringUtils.isEmpty(transportation)){
            return "交通工具为空";
        }
        if(StringUtils.isEmpty(startAddress)){
            return "出发地为空";
        }
        if(StringUtils.isEmpty(endAddress)){
            return "目的地为空";
        }
        if(StringUtils.isEmpty(startGpsx)){
            return "出发地经度为空";
        }
        if(StringUtils.isEmpty(startGpsy)){
            return "出发地纬度为空";
        }
        if(StringUtils.isEmpty(endGpsx)){
            return "目的地经度为空";
        }
        if(StringUtils.isEmpty(endGpsy)){
            return "目的地经度为空";
        }
        return "";
    }
}
