package cn.wxgis.jc.system.web.service;

import java.util.Set;

public interface PermissionService {

    /**
     * 获取角色数据权限
     * @param userId 用户Id
     * @return 角色权限信息
     */
    Set<String> getRolePermission(String userId);

    /**
     * 获取菜单数据权限
     * @param userId 用户Id
     * @return 菜单权限信息
     */
    Set<String> getMenuPermission(String userId);

}
