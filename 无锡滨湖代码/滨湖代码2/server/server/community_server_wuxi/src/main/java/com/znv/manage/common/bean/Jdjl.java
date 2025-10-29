package com.znv.manage.common.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Jdjl {
    private String date;

    private String ticketNumber;

    private String statusStr;

    private String idNumber;

    private Integer recordid;

    private String typeStr;

    private String windowTicketNumber;

    private String name;

    private String time;

    private String collecttime;

    private Integer isAppointment;

    private Integer isGroup;

    private String remarks;

    private String storagetime;
}
