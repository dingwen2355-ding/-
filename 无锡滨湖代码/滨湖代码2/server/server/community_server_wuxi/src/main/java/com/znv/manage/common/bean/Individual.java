package com.znv.manage.common.bean;

import lombok.Data;

import java.util.Date;

@Data
public class Individual {
    private String id;
    private String uid;
    private String account;
    private String individualType="1";
    private String name;
    private String password;
    private String funcs;
    private String gpsInterval;
    private String createBy;
    private String defaultGid;
    private String usrValid;
    private Date createDate;
    private Date startDate;
    private Date validDate;
    private String remarks;
    private String billid;
    private String isOnLine;
    private String type;
    private String groupNum;
    private String icon;
}
