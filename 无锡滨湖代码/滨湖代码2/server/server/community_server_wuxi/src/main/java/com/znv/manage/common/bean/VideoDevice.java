package com.znv.manage.common.bean;

import lombok.Data;

/**
 * 设备类
 */
@Data
public class VideoDevice {
    private String deviceId;
    private String deviceName;
    private String deviceKind;
    private String deviceType;
    private String deviceTypeName;
    private String deviceAddr;
    private String gpsX;
    private String gpsY;
    private String catalogueUrl;

}