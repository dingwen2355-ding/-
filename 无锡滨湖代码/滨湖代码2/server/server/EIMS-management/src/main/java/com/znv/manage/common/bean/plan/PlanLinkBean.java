package com.znv.manage.common.bean.plan;

import java.io.Serializable;

import lombok.Data;
import io.swagger.annotations.ApiModelProperty;

/**
 * 预案关联关系表(TCfgPlanLink)实体类
 *
 * @author makejava
 * @since 2023-05-19 13:48:40
 */
@Data
public class PlanLinkBean implements Serializable {
    private static final long serialVersionUID = 443858906485342406L;

    @ApiModelProperty(value = "主键")
    private Integer id;
    /**
     * 资源类型 1:专家;2:队伍;3:物资;4:场所
     */
    @ApiModelProperty(value = "资源类型 1:专家;2:队伍;3:物资;4:场所")
    private String resourceType;
    /**
     * 预案(t_cfg_plan_info)id/事件预案实体(t_cfg_plan_info_event)id
     */
    @ApiModelProperty(value = "预案(t_cfg_plan_info)id/事件(t_cfg_event)id")
    private String linkId;
    /**
     * 关联主表类型:1:预案;2:事件
     */
    @ApiModelProperty(value = "关联主表类型:1:预案;2:事件")
    private String type;
    /**
     * 关联的资源id
     */
    @ApiModelProperty(value = "关联的资源id")
    private String resourceId;

}
