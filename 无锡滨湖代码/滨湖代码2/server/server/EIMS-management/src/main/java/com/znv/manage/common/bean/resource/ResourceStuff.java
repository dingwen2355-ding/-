package com.znv.manage.common.bean.resource;

import java.io.Serializable;

import lombok.Data;
import io.swagger.annotations.ApiModelProperty;

/**
 * 物资表(TCfgResourceStuff)实体类
 *
 * @author makejava
 * @since 2023-05-19 15:13:42
 */
@Data
public class ResourceStuff implements Serializable {
    private static final long serialVersionUID = 846563564754494791L;
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Integer id;
    /**
     * 物资名称
     */
    @ApiModelProperty(value = "物资名称")
    private String stuffName;
    /**
     * 存放地址
     */
    @ApiModelProperty(value = "存放地址")
    private String address;
    /**
     * 数量
     */
    @ApiModelProperty(value = "数量")
    private Double stuffNum;
    /**
     * 类型
     */
    @ApiModelProperty(value = "类型")
    private String stuffType;

    /**
     * 负责人
     */
    @ApiModelProperty(value = "负责人")
    private String dutyPerson;
    /**
     * 负责电话
     */
    @ApiModelProperty(value = "负责电话")
    private String dutyPhone;
    /**
     * 区域id
     */
    @ApiModelProperty(value = "区域id")
    private String region;
    /**
     * 区域名称
     */
    @ApiModelProperty(value = "区域id")
    private String regionName;
    /**
     * 经度
     */
    @ApiModelProperty(value = "经度")
    private String gpsx;
    /**
     * 纬度
     */
    @ApiModelProperty(value = "纬度")
    private String gpsy;

    /**
     * 物资单位
     */
    @ApiModelProperty(value = "物资单位")
    private String stuffUnit;

    /**
     * 救援类型
     */
    @ApiModelProperty(value = "救援类型")
    private String helpType;

    /**
     * 类型名称
     */
    @ApiModelProperty(value = "救援类型名称")
    private String helpTypeName;

    /**
     * 和事件的距离
     */
    @ApiModelProperty(value = "和事件的距离")
    private Double distance;

}
