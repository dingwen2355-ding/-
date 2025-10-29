package com.znv.manage.common.bean;

import lombok.Data;
import lombok.ToString;


@Data
@ToString
public class DeviceInfo {
    private String deviceId;

    private Double gpsx;

    private Double gpsy;

    private Double distance;

    private String deviceName;

    private String installAddr;

    private String deviceKind;

    private String url;
}
