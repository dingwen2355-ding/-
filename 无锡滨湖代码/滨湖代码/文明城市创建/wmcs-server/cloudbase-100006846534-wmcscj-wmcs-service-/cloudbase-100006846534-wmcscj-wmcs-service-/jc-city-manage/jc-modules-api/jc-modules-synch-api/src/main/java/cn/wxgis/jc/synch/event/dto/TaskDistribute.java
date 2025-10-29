package cn.wxgis.jc.synch.event.dto;

import lombok.Data;

/**
 * 任务分配列表实体
 */
@Data
public class TaskDistribute {

    /**
     * 处置时限
     * 必填
     */
    private String deadline;

    /**
     * 处置要求
     * 必填
     */
    private String finishRemark;

    /**
     * 处置单位 common_deal_group
     * 必填
     */
    private String commonDealGroup;

}
