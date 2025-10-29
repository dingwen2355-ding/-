package com.znv.manage.common.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Title: Fwgdb
 * @Description:
 * @Author: 李贝德
 * @Date: 2021/7/8 14:33
 **/
@Data
public class Fwgdb {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date AssignTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date BeginServiceTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date EndServiceTime;

    private String ID;
    private String LoginName;
    private String CommunityID;
    private String Satisfaction;
    private String PersonName;
    private String PersonTel;
    private String streetID;
    private String OrderCode;
    private String ItemName;

}
