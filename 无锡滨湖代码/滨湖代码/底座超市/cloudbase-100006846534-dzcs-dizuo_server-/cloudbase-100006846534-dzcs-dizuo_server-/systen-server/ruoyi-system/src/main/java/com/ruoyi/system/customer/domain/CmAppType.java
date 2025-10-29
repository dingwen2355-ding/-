package com.ruoyi.system.customer.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.system.customer.CmBaseEntity;
import lombok.Data;

/**
 *
 * @author ruoyi
 */
@Data
@TableName(value = "cm_app_type")
public class CmAppType  extends CmBaseEntity
{

    /**
     * 类型名称
     */
    private String typeName;

    /**
     * 所属模块
     */
    private String appModule;

    /**
     * 状态 0:启用 1: 废弃
     */
    private Integer status;

    /**
     * 排序
     */
    private Integer sortBy;

    /**
     * 统一身份认证应用分类
     */
    private String casAppType;

}
