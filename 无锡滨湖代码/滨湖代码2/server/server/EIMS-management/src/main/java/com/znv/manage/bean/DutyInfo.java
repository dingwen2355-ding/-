package com.znv.manage.bean;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class DutyInfo {
    //企业id
    private String belongCompanyId;
    //姓名
    private String name;
    //单位职务
    private String unitDuty;
    //领导小组职务
    private String teamDuty;
    //联系电话
    private String phone;
}
