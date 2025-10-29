package cn.wxgis.jc.system.web.mapper;

import cn.wxgis.jc.common.web.dao.BaseDao;
import cn.wxgis.jc.system.po.SysRoleMenu;
import cn.wxgis.jc.system.resp.SysRoleMenuResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysRoleMenuMapper extends BaseDao<SysRoleMenu> {

    /**
     * 根据角色id查询列表
     * @param roleId roleId 角色id
     * @return 角色菜单列表
     */
    List<SysRoleMenuResponse> listByRoleId(@Param("roleId") String roleId);

}
