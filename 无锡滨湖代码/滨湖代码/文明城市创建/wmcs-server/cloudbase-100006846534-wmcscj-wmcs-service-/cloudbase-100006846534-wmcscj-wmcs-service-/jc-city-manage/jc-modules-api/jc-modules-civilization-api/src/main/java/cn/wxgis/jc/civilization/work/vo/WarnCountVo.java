package cn.wxgis.jc.civilization.work.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class WarnCountVo {

    private String channelId;

    private String channelName;

    private String type;

    private String typeName;

    private BigDecimal count;

    private String month;

    private String pointId;

    private String pointName;

    private String pointTypeId;

    private String pointTypeName;

    private String regionName;

    private String regionCode;

}
