package com.ruoyi.common.vo.api;

import lombok.Data;

@Data
public class ApplicationByClassificationAndDeptParam {
    /**
     * 应用分类id
     */
    private String applicationClassificationId;
    /**
     * 应用单位id
     */
    private String deptId;

    /**
     * 关键字
     */
    private String keywords;

    /**
     * 当前页
     */
    private Integer current;

    /**
     * 每页数量
     */
    private Integer size;

    private String apiToken;

}
