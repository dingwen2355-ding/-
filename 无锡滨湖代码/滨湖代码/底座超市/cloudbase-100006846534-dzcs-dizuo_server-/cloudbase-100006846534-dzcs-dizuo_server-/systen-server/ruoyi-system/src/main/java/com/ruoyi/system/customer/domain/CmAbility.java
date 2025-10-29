package com.ruoyi.system.customer.domain;

import com.baomidou.mybatisplus.annotation.TableField;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.system.customer.CmBaseEntity;
import lombok.Data;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 *
 * @author ruoyi
 */
@Data
@TableName(value = "cm_ability", autoResultMap = true)
public class CmAbility extends CmBaseEntity
{

    /**
     * 状态 --  0:上架 1:待审核 2:驳回 3:下架 4:撤销
     */
    private Integer status;

    /**
     * 申请理由
     */
    private String applicationReason;


    /**
     * 联系人
     */
    private String principalName;

    /**
     * 联系电话
     */
    private String principalPhone;

    /**
     * 附件信息
     */
    @TableField(jdbcType = JdbcType.VARCHAR,  typeHandler = ListToStringHandler.class)
    private List<UploadInfo> moban;

    /**
     * 申请单位
     */
    private String danwei;

    /**
     * 单位类型
     */
    private String danweiType;

    /**
     * 能力列表
     */
    private String abilityList;

    /**
     * 审核理由
     */
    @TableField(exist = false)
    private String reason;

    @TableField(exist = false)
    private String rowid;

}
