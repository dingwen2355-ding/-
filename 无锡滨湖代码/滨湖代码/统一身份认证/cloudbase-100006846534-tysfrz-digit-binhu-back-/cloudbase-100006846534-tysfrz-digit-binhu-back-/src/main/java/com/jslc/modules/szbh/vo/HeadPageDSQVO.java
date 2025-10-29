package com.jslc.modules.szbh.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author BladeX
 * @since
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "首页", description = "首页")
public class HeadPageDSQVO {



    /**
     * 审批总数
     */
    @ApiModelProperty(value = "审批总数")
    private String approvalsNum;

    /**
     * 申请数量
     */
    @ApiModelProperty(value = "申请数量")
    private String applyForNum;

    /**
     * echarts图标
     */
    @ApiModelProperty(value = "echarts图标")
    private List<MapDSQVO> echarts;


}
