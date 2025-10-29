package com.ruoyi.system.customer.vo;

import lombok.Data;

import java.util.List;

@Data
public class DeptVo {

    private String id;
    private String parentId;
    private String hasChildren;
    private String title;
    private String key;
    private String value;
    private String nodeType;
    private String parentKey;
    private String parentTitle;
    private String deptType;
    private String tenantId;
    private List<DeptVo> children;

}
