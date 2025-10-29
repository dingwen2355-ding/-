package cn.wxgis.jc.system.web.mapper;

import cn.wxgis.jc.common.web.dao.BaseDao;
import cn.wxgis.jc.system.po.SysMenu;
import cn.wxgis.jc.system.resp.SysMenuResponse;
import cn.wxgis.jc.system.select.SysMenuSelect;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysMenuMapper extends BaseDao<SysMenu> {

    /**
     * 查询列表
     * @param select 查询条件
     * @return 菜单列表
     */
    List<SysMenuResponse> voList(SysMenuSelect select);

    /**
     * 查询
     * @param platform
     * @return
     */
    List<SysMenuResponse> getListByMenu(@Param("platform") Integer platform);

    /**
     *
     * @param platform
     * @return
     */
    List<SysMenuResponse> getListByFunction(@Param("platform") Integer platform);

    /**
     * 根据用户id查询拥有菜单
     * @param userId 用户id
     * @return 菜单列表
     */
    List<SysMenuResponse> getMenuTreeByUserId(@Param("userId") String userId, @Param("platform") Integer platform);

    /**
     * 根据用户id查询拥有权限
     * @param userId
     * @return
     */
    List<SysMenuResponse> getFunctionTreeByUserId(@Param("userId") String userId, @Param("platform") Integer platform);

    /**
     * 根据角色ID查询权限
     * @param roleId 角色ID
     * @return 权限列表
     */
    List<String> selectMenuPermsByRoleId(@Param("roleId") String roleId);


    /**
     * 根据用户ID查询权限
     * @param userId 用户ID
     * @return 权限列表
     */
    List<String> selectMenuPermsByUserId(@Param("userId") String userId);

}
