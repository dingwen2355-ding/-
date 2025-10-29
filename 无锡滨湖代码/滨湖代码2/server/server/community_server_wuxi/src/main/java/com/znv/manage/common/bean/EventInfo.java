package com.znv.manage.common.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;
import org.springframework.util.StringUtils;

import java.util.Date;

@Data
@ToString
public class EventInfo {
    private String eventId;

    private String eventType;

    private String eventTypeName;

    private String eventTitle;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date happenTime;

    private String address;

    private String desc;

    private String areaCode;

    private String area;

    private String planId;

    private Double gpsx;

    private Double gpsy;

    private String deviceIds;

    public String checkInfo(){
        if(StringUtils.isEmpty(eventType) || StringUtils.isEmpty(eventTypeName)){
            return "事件类型为空!";
        }
        if(StringUtils.isEmpty(eventTitle)){
            return "事件标题为空!";
        }
        if(StringUtils.isEmpty(happenTime)){
            return "事件发生时间为空!";
        }
        if(StringUtils.isEmpty(desc)){
            return "事件描述为空!";
        }
        if(StringUtils.isEmpty(areaCode) || StringUtils.isEmpty(area)){
            return "事件所属区域为空!";
        }
        return "";
    }
}
