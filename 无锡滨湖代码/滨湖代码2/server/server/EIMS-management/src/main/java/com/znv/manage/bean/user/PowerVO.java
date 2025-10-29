package com.znv.manage.bean.user;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class PowerVO {
    private String userName;
    private String departmentId;
    private String departmentName;
    private String roleName;
    private Map<String,String> roles;
    private List<Map<String,String>> modules;
}
