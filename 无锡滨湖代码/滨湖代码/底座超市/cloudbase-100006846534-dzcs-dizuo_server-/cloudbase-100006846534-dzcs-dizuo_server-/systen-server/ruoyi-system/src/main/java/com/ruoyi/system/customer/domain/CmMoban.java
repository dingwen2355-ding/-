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
@TableName(value = "cm_moban", autoResultMap = true)
public class CmMoban extends CmBaseEntity
{

    /**
     * 模板名称
     */
    private String name;

    /**
     * 模板类型
     */
    private String type;


    /**
     * 是否使用 0: 否 1: 是
     */
    private String isUse;


    /**
     * 附件
     */
    @TableField(jdbcType = JdbcType.VARCHAR,  typeHandler = ListToStringHandler.class)
    private List<UploadInfo> upload;

    /**
     * 说明
     */
    private String miaoshu;

    /**
     * 上传用户
     */
    private String yonghu;

}
