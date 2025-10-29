package com.znv.manage.bean.user;

public enum RoleEnum {


    SUPERADMIN("1", "超级管理员"),
    ZUOXIADMIN("10", "街道坐席人员"),
    PRECINCTADMIN("20", "街道管理员"),
    PRECINCTWORKER("30", "责任部门"),
    GRIDADMIN("40", "社区管理员"),
    GRIDOPERATOR("50", "网格员");

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
