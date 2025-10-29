package com.znv.manage.bean.user;

public enum PrecinctEnum {


    YJADMIN("37020302501", "应急局管理员"),
    SHEQUROLE("50", "社区角色"),
    SUPERADMIN("370203025", "超级管理员");
    private String id;
    private String name;

    private PrecinctEnum(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }
}
