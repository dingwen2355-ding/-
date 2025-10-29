package com.wxgis.topic.vo;

import lombok.Data;

import java.util.List;

@Data
public class Overlying {

    private List<CoordInfos> CoordInfos;

    private String FlowId;

    private String DecodeTime;

    private String StayTime;
}
