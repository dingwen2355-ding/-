package com.znv.manage.common.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.Date;


@Data
public class ArrivedResource {

    private String id;
    private String eventId;
    private String unitId;
    private String resourceId;
    private String resourceName;
    private String type;
    private String count;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date arriveTime;

    public String checkInfo(){
        if(StringUtils.isEmpty(eventId)){
            return "事件Id为空。";
        }
        if(StringUtils.isEmpty(unitId)){
            return "委办局Id为空。";
        }
        if(StringUtils.isEmpty(resourceId) && StringUtils.isEmpty(resourceName)){
            return "资源力量Id和资源力量名同时为空。";
        }
        if(StringUtils.isEmpty(count)){
            return "资源力量数量为空";
        }
        if(StringUtils.isEmpty(arriveTime)){
            return "资源力量到场时间为空";
        }
        return "";
    }
}
