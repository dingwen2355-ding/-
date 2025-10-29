package com.jslc.modules.monitor.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author lc
 * @title: ApiStatisticsVO
 * @projectName digit-binhu-back
 * @date 2023/10/8 15:45
 */
@Data
public class ApiStatisticsVO {
    @ApiModelProperty("接口名称")
    String apiName;
    @ApiModelProperty("接口当天调用次数")
    ApiVO dayCallApiAmount;
    @ApiModelProperty("接口每小时调用次数")
    List<ApiVO> hourCallApiAmount;
}
