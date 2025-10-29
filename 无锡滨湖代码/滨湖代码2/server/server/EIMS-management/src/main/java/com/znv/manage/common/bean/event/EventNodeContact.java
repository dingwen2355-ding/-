package com.znv.manage.common.bean.event;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * (TCfgEventNodeContact)实体类
 *
 * @author makejava
 * @since 2022-12-09 16:32:32
 */
@Data
public class EventNodeContact implements Serializable {
    private static final long serialVersionUID = -70587165775152197L;

    private Integer id;

    @ApiModelProperty(value = "事件id")
    private Integer eventId;

    @ApiModelProperty(value = "节点id")
    private Integer nodeId;

    @ApiModelProperty(value = "节点名称")
    private String nodeName;

    @ApiModelProperty(value = "是否隐藏")
    private String isHidden;

    @ApiModelProperty(value = "排序")
    private Integer order;

    @ApiModelProperty(value = "事件任务列表")
    private List<EventTask> eventTaskList;

}
