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
@TableName(value = "cm_mobandownload")
public class CmMobanDownload extends CmBaseEntity
{


    /**
     * 应用编号
     */
    private String appId;

}
