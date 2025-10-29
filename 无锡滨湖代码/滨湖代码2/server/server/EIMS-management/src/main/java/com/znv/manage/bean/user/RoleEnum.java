package com.znv.manage.bean.user;

public enum RoleEnum {


    SUPERADMIN("1", "超级管理员"),
    PRECINCTADMIN("2", "街镇管理员"),
    PRECINCTWORKER("3", "街镇工作人员"),
    GRIDADMIN("4", "网格管理员"),
    GRIDOPERATOR("5", "网格员");

    private String id;
    private String name;

    private RoleEnum(String id, String name) {
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
