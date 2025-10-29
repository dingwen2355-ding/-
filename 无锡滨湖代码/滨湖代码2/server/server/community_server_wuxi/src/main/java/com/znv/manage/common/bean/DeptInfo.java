package com.znv.manage.common.bean;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class DeptInfo {

    private String deptId;

    private String name;

    private String parentId;

}
