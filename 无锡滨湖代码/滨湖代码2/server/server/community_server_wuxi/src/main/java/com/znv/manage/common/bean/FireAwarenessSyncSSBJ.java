package com.znv.manage.common.bean;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class FireAwarenessSyncSSBJ {
    private Integer id;

    private String deviceType;

    private String userlist;

    private String callAddress;

    private String pxpy;

    private String alarmTime;

    private String deviceId;

    private String deviceStatus;

    private String positionName;

    private Integer loopType;

    private Integer valueType;

    private String deviceValue;

    private Integer unitId;

    private String collectTime;

    private Integer zoneBit;

    private String createTime;
}
