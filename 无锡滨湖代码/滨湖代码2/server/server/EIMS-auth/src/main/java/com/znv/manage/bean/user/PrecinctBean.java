package com.znv.manage.bean.user;

import lombok.Data;

@Data
public class PrecinctBean {
    private String precinctId;

    private String precinctName;

    private String upPrecinctId;

    private int precinctKind;

    private String regionName;

    private String streetName;

    private String streetNo;

    private String buildingNo;

    private String unitNo;

    private String floorNo;

    private String houseNo;
    private String mapAddr;

    private String gpsX;

    private String gpsY;

    private String regionXY;

    private String precinctAddr;
    private String houseArea;
}
