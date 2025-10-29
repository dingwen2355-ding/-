package com.jslc.modules.monitor.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author lc
 * @title: JvmVO
 * @projectName digit-binhu-back
 * @description: JVM监控
 * @date 2023/10/8 14:45
 */
@Data
public class JvmVO {
    /**
     * 服务名称
     */
    @ApiModelProperty("服务名称")
    private String serverName;
    /**
     * 总jvm内存
     */
    @ApiModelProperty("总jvm内存")
    private BigDecimal totalMemory;
    /**
     * 已用jvm内存
     */
    @ApiModelProperty("已用jvm内存")
    private BigDecimal usedMemory;
    /**
     * 空闲jvm内存
     */
    @ApiModelProperty("空闲jvm内存")
    private BigDecimal freeMemory;
}
