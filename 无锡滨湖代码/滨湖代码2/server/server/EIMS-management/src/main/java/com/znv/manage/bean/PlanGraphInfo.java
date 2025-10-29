package com.znv.manage.bean;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PlanGraphInfo {
    private String id;
    //企业id
    private String belongCompanyId;
    //类型
    private String fileType;
    //附件
    private String file;
}
