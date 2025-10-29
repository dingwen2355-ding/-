package com.znv.manage.common.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Title: Yjfkb
 * @Description:预警反馈表实体类
 * @Author: 李贝德
 * @Date: 2021/7/1 15:00
 **/
@Data
public class Yjfkb {

    private String id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date backTime;
    private Date createTime;

    private String persons;
    private String imgListStr;
    private String reportId;
    private String author;
    private String goods;
    private String remark;

}
