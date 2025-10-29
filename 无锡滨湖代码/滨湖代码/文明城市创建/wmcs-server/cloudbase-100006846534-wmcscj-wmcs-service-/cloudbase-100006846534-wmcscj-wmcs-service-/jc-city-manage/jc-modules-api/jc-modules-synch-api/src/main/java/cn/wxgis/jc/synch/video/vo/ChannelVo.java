package cn.wxgis.jc.synch.video.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 通道实体
 */
@Data
public class ChannelVo {


    /**
     * 节点类型。2:设备，3:通道。nodeType的值不同，解析的字段也不同。
     */
    private Integer nodeType;

    /**
     * 通道编码
     */
    private String id;


    /**
     * 通道名称
     */
    private String name;

    /**
     * 组织类型，"1"为基本组织
     */
    private String orgType;

    /**
     * 是否是父节点（是否有子节点），true:是父节点，false:不是父节点
     */
    private boolean isParent;

    /**
     * 通道所属设备编码(和设备的id相同)
     */
    private String parentId;

    /**
     * 组织编码，根组织为""
     */
    private String orgCode;

    /**
     * 排序码
     */
    private Integer sort;


    /**
     * 和id一样
     */
    private String channelId;

    /**
     * 通道是否在线。"1":在线；"0":离线
     */
    private String online;

    /**
     * 通道是否在线。1:在线 0:离线
     */
    private Integer status;

    /**
     * 摄像头类型，"1":枪机，"2":球机，"3":半球等
     */
    private String cameraType;

    /**
     * 编码通道功能，一个通道如果支持多种功能，用","分隔。编码通道功能
     */
    private String cameraFunctions;

    /**
     * 和parentId一样
     */
    private String deviceCode;

    /**
     * 通道序号，从0开始
     */
    private Integer channelSeq;

    /**
     * 参考通道类型
     */
    private String channelType;

    /**
     * 通道所在地图坐标的X值
     */
    private BigDecimal gpsX;

    /**
     * 通道所在地图坐标的Y值
     */
    private BigDecimal gpsY;

    /**
     * 通道所属的单元类型，参考单元类型
     */
    private String unitType;

    /**
     * 所属的paas编号，此值为paas基础平台所定义
     */
    private String paasId;

    /**
     * 智能状态，0:非智能 1:智能
     */
    private Integer intelliState;

}
