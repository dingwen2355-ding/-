package com.znv.manage.common.bean.resource;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class Protection {
    /**
     * 主键
     */
    private Long id;

    /**
     * 防护名称
     */
    private String protectionName;

    /**
     * 防护类型
     */
    private String protectionType;

    /**
     * 目标地址
     */
    private String destinationAddress;

    /**
     * 管理人姓名
     */
    private String managerName;

    /**
     * 联系电话
     */
    private String managerPhone;

    /**
     * 最近巡查时间
     */
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date lastInspection;
}
