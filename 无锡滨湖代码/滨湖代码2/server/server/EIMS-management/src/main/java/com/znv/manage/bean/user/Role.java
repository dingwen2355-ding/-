package com.znv.manage.bean.user;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Role {
    public static final String ALLow_ALL ="1";
    public static final String ALLow_OWNER ="2";
    private String roleId;
    private String roleName;
    //暂时不分层，可为空
    private String upRoleId;
    private String description;
    //可以访问的数据集
    private String dataPermission;
    private String level;
    //角色关联的模块集合
    private List<Module> modules = new ArrayList<>();

    public Role() {

    }

    public Role(
            String roleId,
            String roleName,
            String upRoleId,
            String description,
            String dataPermission,
            String level
    ) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.upRoleId = upRoleId;
        this.description = description;
        this.dataPermission = dataPermission;
        this.level = level;
    }
}
