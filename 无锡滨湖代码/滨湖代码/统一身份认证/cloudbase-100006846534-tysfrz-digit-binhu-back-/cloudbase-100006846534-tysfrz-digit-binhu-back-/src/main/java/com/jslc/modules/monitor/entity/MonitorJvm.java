package com.jslc.modules.monitor.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * jvm监控
 * </p>
 *
 * @author Auto-generator
 * @since 2023-10-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("monitor_jvm")
public class MonitorJvm extends Model<MonitorJvm> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 服务名称
     */
    private String serverName;
    /**
     * 总jvm内存
     */
    private BigDecimal totalMemory;
    /**
     * 已用jvm内存
     */
    private BigDecimal usedMemory;
    /**
     * 空闲jvm内存
     */
    private BigDecimal freeMemory;
    /**
     * 服务器地址
     */
    private String serverIp;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;



}
