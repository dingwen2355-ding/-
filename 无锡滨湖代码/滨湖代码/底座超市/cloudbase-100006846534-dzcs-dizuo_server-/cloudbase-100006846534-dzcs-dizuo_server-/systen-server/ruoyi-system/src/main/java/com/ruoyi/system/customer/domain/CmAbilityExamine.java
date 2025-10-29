package com.ruoyi.system.customer.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.enums.SendMessageEnum;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.customer.CmBaseEntity;
import com.ruoyi.system.domain.SysNotice;
import lombok.Data;
import org.apache.ibatis.type.JdbcType;

import java.util.Date;
import java.util.List;

/**
 *
 * @author ruoyi
 */
@Data
@TableName(value = "cm_Ability_examine", autoResultMap = true)
public class CmAbilityExamine extends CmBaseEntity
{


    /**
     * 能力id
     */
    private String cmAbilityId;

    /**
     * 审核类型 0: 通过 2: 驳回
     */
    private String examineType;

    /**
     * 理由
     */
    private String reason;

    /**
     * 审核人
     */
    private String shenheren;

    /**
     * 附件信息
     */
    @TableField(jdbcType = JdbcType.VARCHAR,  typeHandler = ListToStringHandler.class)
    private List<UploadInfo> upload;

    /**
     * 审核时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT,updateStrategy= FieldStrategy.NEVER)
    private Date shenheTime;


}
