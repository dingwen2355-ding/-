package com.ruoyi.system.customer.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.system.customer.CmBaseEntity;
import lombok.Data;

/**
 *
 * @author ruoyi
 */
@Data
@TableName(value = "cm_app_examine")
public class CmAppExamine extends CmBaseEntity
{

    /**
     * 类型名称
     */
    private String cmAppId;

    /**
     * 审核类型 0: 通过 2: 驳回
     */
    private String examineType;


}
