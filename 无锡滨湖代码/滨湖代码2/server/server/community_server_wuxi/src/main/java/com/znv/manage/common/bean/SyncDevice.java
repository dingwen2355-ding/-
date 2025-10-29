package com.znv.manage.common.bean;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SyncDevice {

    //设备id
    private String deviceId;

    //设备名称
    private String deviceName;

    //经度
    private String longitude;

    //纬度
    private String latitude;

    //距离
    private Double distance;

}
