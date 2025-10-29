package cn.wxgis.jc.synch.video.vo;

import lombok.Data;


/**
 * 设备实体
 */
@Data
public class DeviceVo {

    /**
     * 节点类型。2:设备，3:通道。nodeType的值不同，解析的字段也不同。
     */
    private Integer nodeType;

    /**
     * 设备编码
     */
    private String id;

    /**
     * 节点名称
     */
    private String name;

    /**
     * 界面显示的图标名称
     */
    private String icon;

    /**
     * 组织类型，"1"为基本组织
     */
    private String orgType;

    /**
     * 是否是父节点（是否有子节点），true:是父节点，false:不是父节点
     */
    private boolean isParent;

    /**
     * 所属组织的ID，如果属于root节点则为""
     */
    private String parentId;

    /**
     * 和parentId一样
     */
    private String orgCode;

    /**
     * 排序码
     */
    private Integer sort;




    /**
     * 设备是否在线。"1":在线；"0":离线
     */
    private String online;

    /**
     * 版本
     */
    private String version;

    /**
     * 设备大类，参考设备大类
     */
    private String category;

    /**
     * 设备编码
     */
    private String deviceId;

    /**
     * 智能状态，0:非智能 1:智能
     */
    private Integer intelliFlag;

    /**
     * 参考厂商
     */

    private String subType;

    /**
     * 参考设备小类
     */
    private String manufacturer;

    /**
     * 设备IP
     */
    private String ip;

}
