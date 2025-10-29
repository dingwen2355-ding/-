package com.znv.manage.common.bean.lawInspect;

import lombok.Data;

import java.util.List;

@Data
public class LawInspect {
    private Integer mainId;

    //对应执法计划Name(t_cfg_law_plan)
    private String planName;

    //检查来源
    private String inspectSource;

    //检查对象名(resource_company)
    private String objectName;

    //检查日期
    private String inspectDate;

    /**
     * 执法人员1name(t_cfg_law_enforcer)
     */
    private String enforcerFirstName;


    /**
     * 执法人员2name(t_cfg_law_enforcer)
     */
    private String enforcerSecondName;

    //监管类型(企业类型,resource_company)
    private String companyType;

    //所属辖区(resource_company)
    private String regionName;

    private List<LawInspectObject> lawInspectObjectList;
}