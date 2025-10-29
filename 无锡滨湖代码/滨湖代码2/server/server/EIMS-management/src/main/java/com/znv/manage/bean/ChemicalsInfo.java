package com.znv.manage.bean;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ChemicalsInfo {
    //id
    private String id;
    //类别（编号）
    private String mainTypeId;
    //类别（编号名称）
    private String mainTypeName;
    //项目（编号）
    private String subTypeId;
    //项目（编号名称）
    private String subTypeName;
    //危险货物编号
    private String stuffCode;
    //品名
    private String name;
    //别名
    private String alias;
    //英文名
    private String nameInEn;
    //英文别名
    private String aliasInEn;
    //CAS号
    private String cas;
    //UN号
    private String unCode;
    //是否重点监管
    private String isKey;
    //特别警示/风险提示
    private String riskTips;
    //理化特性
    private String physicochemical;
    //危害信息
    private String harm;
    //安全措施
    private String safetyMeasures;
    //处置原则
    private String principle;
    //危化品储量
    private String unit;
    //危化品计量单位
    private String count;
    //备注
    private String remark;
}
