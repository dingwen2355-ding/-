package com.znv.manage.common.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.models.auth.In;
import lombok.Data;

import java.util.Date;

/**
 * @Title: AISxt
 * @Description:
 * @Author: 李贝德
 * @Date: 2021/7/3 10:49
 **/
@Data
public class AIsxt {

    private Integer id;

    private Date createTime;

    private String cameraID;

    private String typeCode;

    private String superiorCode;

    private String inputStyle;

    private String region;

    private String pixel;

    private String cameraName;
}
