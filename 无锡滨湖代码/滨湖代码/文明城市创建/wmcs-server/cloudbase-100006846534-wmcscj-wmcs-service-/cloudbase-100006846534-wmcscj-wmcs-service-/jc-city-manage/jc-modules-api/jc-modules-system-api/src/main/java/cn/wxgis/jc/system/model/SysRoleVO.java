package cn.wxgis.jc.system.model;

import cn.wxgis.jc.system.po.SysRole;
import lombok.Data;

import java.util.Set;

@Data
public class SysRoleVO extends SysRole {

    /** 角色菜单权限 */
    private Set<String> permissions;

}
