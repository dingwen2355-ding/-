package com.znv.manage.common.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author SunJiaHao
 * @date 2022-08-16
 * @Description 第三方系统平台实体类（对应表 t_cfg_third_platform）
 */
@Data
public class ThirdPlatformEntity {

    /**
     * 主键, 自增id
     */
    private Long id;

    /**
     * 系统名称
     */
    private String name;

    /**
     * 系统地址
     */
    private String address;

    /**
     * 参数 json
     */
    private String params;

    /**
     * 系统来源
     */
    private String source;

    /**
     * 图片
     */
    private String image;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

}
