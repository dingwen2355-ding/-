package com.znv.manage.bean;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class HazardSourceInfo {
    //id
    private String id;
    //所属企业id
    private String belongCompanyId;
    //危险源名称
    private String hazardSourcesName;
    //危险源等级
    private String hazardSourcesLevel;
    //危险源等级名称
    private String hazardSourcesLevelName;
    //危险源类型
    private String hazardSourcesType;
    //危险源类型名称
    private String hazardSourcesTypeName;
    //危险化工工艺
    private String chemicalProcess;
    //危险化工工艺名称
    private String chemicalProcessName;
    //涉及危化品
    private List<ChemicalsInfo> chemicals;
    //涉及危化品名称
    private String chemicalsName;
    //危险源描述
    private String hazardDesc;
    //危险源分布图
    private String file;
}
