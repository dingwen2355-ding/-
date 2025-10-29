package com.ruoyi.system.customer.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.system.customer.CmBaseEntity;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

/**
 *
 * @author ruoyi
 */
@Data
@TableName(value = "cm_duan_xin_switch")
public class CmDuanXinSwitch
{

    @TableId
    private int id;

    private int status;


    private Long createBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT,updateStrategy= FieldStrategy.NEVER)
    private Date createTime;

    /** 更新者 */
    private Long updateBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;


}
