package com.znv.manage.common.bean;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class FireAwarenessSyncJCSJ {
    private Integer dataId;

    private String deviceType;

    private String loopType;

    private String alarmTime;

    private Integer valueType;

    private String deviceValue;

    private Integer id;

    private String collectTime;

    private String deviceId;

    private Integer zoneBit;

    private Integer deviceStatus;

    private String createTime;
}
