package cn.wxgis.jc.synch.video.vo;

import lombok.Data;

import java.util.List;

@Data
public class DeviceTreeVo {

    /**
     * 节点类型。1:组织
     */
    private Integer nodeType;

    /**
     * 组织编码，根组织为""
     */
    private String id;

    /**
     * 组织名称
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
     * 父节点ID，如果父节点是root节点则为""
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
     * 组织自定义编码，通常用于国标编码
     */
    private String orgSn;

    /**
     * 和orgSn一样
     */
    private String sn;

    /**
     * 级联域Id，字段为空或者0表示本级
     */
    private Long domainId;


    private List<DeviceTreeVo> children;

}
