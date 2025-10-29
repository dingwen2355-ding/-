package com.znv.manage.bean;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SaferInfo {
    //所属公司编号
    private String id;
    //所属公司编号
    private String belongCompanyId;
    //安全员姓名
    private String safetyOfficerName;
    //安全员性别
    private String safetyOfficerSex;
    //安全员联系方式
    private String safetyOfficerPhone;
    //持有资格证及编号描述
    private String certificateDesc;
    //备注
    private String contents;
}
