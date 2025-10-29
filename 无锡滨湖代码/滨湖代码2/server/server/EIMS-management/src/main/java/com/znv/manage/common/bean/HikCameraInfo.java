package com.znv.manage.common.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 海康摄像头实体类
 *
 * @title: HikCameraInfo
 * @Author yf
 * @Date: 2022/5/30 16:52
 */
@Data
public class HikCameraInfo {

    /**
     * 摄像头id
     */
    @JSONField(name = "device_id")
    private String deviceId;

    /**
     * 摄像头名称
     */
    @JSONField(name = "device_name")
    private String deviceName;

    /**
     * 设备类型 ( 1: 高清; 2:高清云台 )
     */
    @JSONField(name = "device_type")
    private int deviceType;

    /**
     * 所在辖区
     */
    @JSONField(name = "precinct_id")
    private String precinctId;

    /**
     * 方向
     */
    @JSONField(name = "tag")
    private String tag;

    /**
     * 所属单位
     */
    @JSONField(name = "unit")
    private String unit;

    /**
     * 经度
     */
    @JSONField(name = "gpsx")
    private String gpsx;

    /**
     * 纬度
     */
    @JSONField(name = "gpsy")
    private String gpsy;

    /**
     * 更新时间
     *
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    @JSONField(name = "replace_date")
    private Date replaceDate;

    @JSONField(name = "remark")
    private String remark;

}
