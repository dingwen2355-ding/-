package com.znv.manage.common.bean.resource;

import lombok.Data;

@Data
public class Law {
    /**
     * 主键
     */
    private Long id;

    /**
     * 法律名称
     */
    private String lawName;

    /**
     * 法规分类
     */
    private String lawClass;

    /**
     * 违法行为描述
     */
    private String illegalDescription;

    /**
     * 法律规定
     */
    private String legalProvisions;

    /**
     * 规定条款
     */
    private String prescribedTerms;

    /**
     * 处罚依据条款
     */
    private String penaltyBasisClause;

    /**
     * 处罚依据名称
     */
    private String penaltyBasisName;

    /**
     * 处罚依据
     */
    private String penaltyBasis;

    /**
     * 实施标准
     */
    private String standards;

    /**
     * 事故类型
     */
    private String accidentType;
}
