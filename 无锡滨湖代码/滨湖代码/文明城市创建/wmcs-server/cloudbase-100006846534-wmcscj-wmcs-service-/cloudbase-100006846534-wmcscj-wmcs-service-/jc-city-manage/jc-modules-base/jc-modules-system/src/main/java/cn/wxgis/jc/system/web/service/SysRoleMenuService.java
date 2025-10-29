package cn.wxgis.jc.system.web.service;

import cn.wxgis.jc.common.web.service.BaseService;
import cn.wxgis.jc.system.po.SysRoleMenu;
import cn.wxgis.jc.system.resp.SysRoleMenuResponse;

import java.util.List;

public interface SysRoleMenuService extends BaseService<SysRoleMenu> {

    /**
     * 查询列表
     * @param roleId 角色id
     * @return 角色菜单列表
     */
    List<SysRoleMenuResponse> list(String roleId);

}
