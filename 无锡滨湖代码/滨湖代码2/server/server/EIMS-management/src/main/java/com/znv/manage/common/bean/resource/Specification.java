package com.znv.manage.common.bean.resource;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class Specification {
    /**
     * 主键
     */
    private Long id;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 编制单位
     */
    private String preparationUnit;

    /**
     * 安全管理类型
     */
    private String managementType;

    /**
     * 监管行业
     */
    private String regulatoryIndustry;

    /**
     * 编制日期
     */
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private String preparedDate;

    /**
     * 生效日期
     */
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private String effectiveDate;

    /**
     * 文档类型
     */
    private String documentType;

    /**
     * 内容摘要
     */
    private String contentSummary;

    /**
     * 备注
     */
    private String notes;

    /**
     * 附件
     */
    private String annexUrl;
}
