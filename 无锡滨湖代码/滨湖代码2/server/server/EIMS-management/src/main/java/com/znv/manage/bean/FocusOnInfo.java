package com.znv.manage.bean;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class FocusOnInfo {
    //企业id
    private String belongCompanyId;
    //关注类型
    private String emphasisType;
    //关注类型名称
    private String emphasisTypeName;
    //涉及物资
    private String stuffName;
    //数量/储量
    private String count;
    //简介/说明
    private String processIntroduction;
}
