package com.znv.manage.bean.user;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 企业评价表 resource_company_evaluate
 *
 * @author yangbo
 * @email
 * @date 2022-02-17 09:15:35
 */
@Data
//@TableName("resource_company_evaluate")
public class EvaluateEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @ApiModelProperty(value = "编号")
    private Integer id;
    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    private String name;
    /**
     * 评价分类
     */
    @ApiModelProperty(value = "评价分类")
    private String evaluateType;
    /**
     * 企业名称
     */
    @ApiModelProperty(value = "企业名称")
    private String companyName;
    private String companyId;
    /**
     * 评价单位
     */
    @ApiModelProperty(value = "评价单位")
    private String evaluateUnit;
    /**
     * 评价时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "评价时间")
    private Date evaluateTime;
    /**
     * 有效期
     */
    @ApiModelProperty(value = "有效期")
    private String periodOfValidity;
    /**
     * 评价专家
     */
    @ApiModelProperty(value = "评价专家")
    private String evaluateSpecialist;
    /**
     * 中标价格（评价费用）
     */
    @ApiModelProperty(value = "中标价格（评价费用）")
    private String evaluateAmount;
    /**
     * 评价内容（简要描述）
     */
    @ApiModelProperty(value = "评价内容（简要描述）")
    private String evaluateContent;
    /**
     * 评价附件
     */
    @ApiModelProperty(value = "评价附件")
    private String evaluateFile;

}
