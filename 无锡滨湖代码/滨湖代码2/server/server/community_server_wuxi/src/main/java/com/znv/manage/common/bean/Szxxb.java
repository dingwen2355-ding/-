package com.znv.manage.common.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Title: Szxxb
 * @Description:
 * @Author: 李贝德
 * @Date: 2021/7/8 14:28
 **/
@Data
public class Szxxb {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date deadDate;

    private String deadName;
    private String idCardNumber;

}
