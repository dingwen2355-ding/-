package com.wxgis.topic.vo;

import lombok.Data;

import java.util.List;

@Data
public class CoordInfos {

    private String ShapeType;

    private String LineColor;

    private String CoordX;

    private String CoordY;

    private String CoordWidth;

    private String CoordHeight;

    private String Reserved;

    private List<Points> Points;

}
