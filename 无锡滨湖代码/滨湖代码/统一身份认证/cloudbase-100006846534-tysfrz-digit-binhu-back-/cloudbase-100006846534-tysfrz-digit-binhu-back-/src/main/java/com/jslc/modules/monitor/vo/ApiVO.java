package com.jslc.modules.monitor.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lc
 * @title: ApiVO
 * @projectName digit-binhu-back
 * @date 2023/10/8 15:31
 */
@Data
public class ApiVO {
    @ApiModelProperty("接口调用次数")
    int apiAmount;
    @ApiModelProperty("接口调用时间")
    String time;
    @JsonIgnoreProperties
    @ApiModelProperty(hidden = true)
    String api;
}
