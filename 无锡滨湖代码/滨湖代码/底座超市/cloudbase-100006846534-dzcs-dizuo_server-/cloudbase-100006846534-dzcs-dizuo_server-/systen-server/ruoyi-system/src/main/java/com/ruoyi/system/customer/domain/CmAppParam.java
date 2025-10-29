package com.ruoyi.system.customer.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class CmAppParam {
    /**
     * 所属模块
     */
    private String appModule;

    /**
     * 应用类型
     */
    private String appType;

    /**
     * 应用来源
     */
    private String  source;

    /**
     * 上架时间，关联表cm_app_examine中的update_time
     */
    private String shangjiatime;

    /**
     * 名称
     */
    private String appName;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 是否推荐按
     */
    private String recommendFlag;

}
