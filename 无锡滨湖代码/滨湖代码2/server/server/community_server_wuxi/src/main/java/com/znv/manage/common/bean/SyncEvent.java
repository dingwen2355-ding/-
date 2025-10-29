package com.znv.manage.common.bean;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SyncEvent {

    //事件id
    private String flowNo;

    //事件类别
    private String eventType;

    private String eventTypeName;

    public String get$class_2$() {
        return $class_2$;
    }

    public void set$class_2$(String $class_2$) {
        this.$class_2$ = $class_2$;
    }

    //事件类别
    private String $class_2$;

    //事件时间
    private String reportDate;

    //标题
    private String questionTitle;

    //描述
    private String desc;

    //地址
    private String addr;

    //经度
    private String lat;

    //纬度
    private String lng;

    //状态
    private String flowStatus;

    //状态
    private String eventStatus;

    //受理意见
    private String shouliyijian;

    //结案意见
    private String finishRemark;

    private String updateDate;

    private String remark;

    private String emergency;

    private String operationContent;

    private String fromNodeId;

    private String orderNodeId;

    private String createDate;

    private String weightyincident;

    private String precinctId;

    private String city;

}
