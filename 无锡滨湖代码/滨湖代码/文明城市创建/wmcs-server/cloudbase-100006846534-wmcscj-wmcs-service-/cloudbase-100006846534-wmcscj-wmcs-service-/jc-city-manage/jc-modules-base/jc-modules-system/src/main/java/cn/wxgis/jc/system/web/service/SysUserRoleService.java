package cn.wxgis.jc.system.web.service;

import cn.wxgis.jc.common.web.service.BaseService;
import cn.wxgis.jc.system.model.SysRoleVO;
import cn.wxgis.jc.system.po.SysRole;
import cn.wxgis.jc.system.po.SysUserRole;

import java.util.List;

public interface SysUserRoleService extends BaseService<SysUserRole> {

    /**
     * 保存用户角色列表
     * @param userId
     * @param roles
     * @return 新增条数
     */
    int saveByUser(String userId, List<String> roles);

    /**
     * 删除用户的关联数据
     * @param userId 用户id
     * @return 删除条数
     */
    int deleteByUser(String userId);

    /**
     * 根据用户id查询数据
     * @param userId 用户id
     * @return 角色列表
     */
    List<SysRole> selectRoleListByUserId(String userId);

    /**
     * 根据用户id查询数据
     * @param userId 用户id
     * @return 角色列表
     */
    List<SysRoleVO> selectRoleVoListByUserId(String userId);

    /**
     * 根据用户id查询角色名称
     * @param userId
     */
    String getRoleNamesByUserId(String userId);
}
