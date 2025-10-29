package com.znv.manage.common.bean.lawInspect;

import lombok.Data;

import java.util.Date;

/**
 * 监管执法-检查标准
 */
@Data
public class InspectStandard {

    private Integer id;

    //检查项目
    private String project;

    //检查内容
    private String content;

    //执法依据
    private String lawBasic;

    //方式方法
    private String method;

    //处置依据
    private String disposalBasis;

    //检查层面
    private String inspectLevel;

    //所属机构id
    private String precinctId;

    //所属机构名
    private String precinctName;

    //标准
    private String standard;

    private Date createTime;
}