package cn.wxgis.jc.system.web.service;

import cn.wxgis.jc.common.domain.exists.ExistsReturn;
import cn.wxgis.jc.common.web.service.BaseService;
import cn.wxgis.jc.system.po.SysMenu;
import cn.wxgis.jc.system.req.SysMenuSaveRequest;
import cn.wxgis.jc.system.resp.LoginMenuResponse;
import cn.wxgis.jc.system.resp.SysMenuResponse;
import cn.wxgis.jc.system.select.SysMenuSelect;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface SysMenuService extends BaseService<SysMenu> {

    /**
     * 新增
     * @param request 菜单信息
     * @return 新增条数
     */
    int save(SysMenuSaveRequest request);

    /**
     * 修改
     * @param request 菜单信息
     * @return 修改条数
     */
    int edit(SysMenuSaveRequest request);

    /**
     * 删除
     * @param id 菜单id
     * @return 删除条数
     */
    int delete(String id);

    /**
     * 验证唯一性
     * @param existsParam 验证参数
     * @return 结果
     */
    ExistsReturn exists(Map<String, String> existsParam);

    /**
     * 查询列表
     * @param select 查询条件
     * @return 菜单列表
     */
    List<SysMenuResponse> listMenuTree(SysMenuSelect select);

    /**
     * 根据id查询数据
     * @param id 菜单id
     * @return 菜单信息
     */
    SysMenuResponse findResponseById(String id);

    /**
     * 根据用户id查询菜单路由数据
     * @param userId
     * @return
     */
    LoginMenuResponse getMenuTreeByUserId(String userId, Integer platform);

    /**
     * 根据角色ID查询权限
     * @param roleId 角色ID
     * @return 权限列表
     */
    Set<String> selectMenuPermsByRoleId(String roleId);

    /**
     * 根据用户ID查询权限
     * @param userId 用户ID
     * @return 权限列表
     */
    Set<String> selectMenuPermsByUserId(String userId);

    /**
     * 查询菜单下是否存在菜单
     * @param id 菜单id
     * @return 结果
     */
    boolean hasChildByMenuId(String id);

    /**
     * 检查菜单是否绑定角色
     * @param id 菜单id
     * @return 结果
     */
    boolean checkMenuExistRole(String id);

}
