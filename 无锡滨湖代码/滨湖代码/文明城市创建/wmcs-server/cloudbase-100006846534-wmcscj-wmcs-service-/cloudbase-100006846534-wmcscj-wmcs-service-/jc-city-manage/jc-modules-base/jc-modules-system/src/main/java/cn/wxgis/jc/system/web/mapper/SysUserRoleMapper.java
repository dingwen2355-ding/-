package cn.wxgis.jc.system.web.mapper;

import cn.wxgis.jc.common.web.dao.BaseDao;
import cn.wxgis.jc.system.po.SysRole;
import cn.wxgis.jc.system.po.SysUserRole;
import cn.wxgis.jc.system.resp.SysUserRoleResponese;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysUserRoleMapper extends BaseDao<SysUserRole> {

    /**
     * 根据用户id查询数据
     * @param userId 用户id
     * @return 角色列表
     */
    List<SysRole> selectRoleListByUserId(@Param("userId") String userId);

    /**
     * 根据用户id列表查询数据
     * @param userIds 用户id集合
     * @return 角色列表
     */
    List<SysUserRoleResponese> selectRoleListByUserIds(@Param("userIds") List<String> userIds);

    /**
     * 查询用户角色名称列表
     * @param userId 用户id
     * @return 用户角色名称列表
     */
    List<String> selectRoleNamesByUserId(@Param("userId") String userId);

    /**
     * 根据用户id查询角色名称
     * @param userId
     */
    String getRoleNamesByUserId(@Param("userId") String userId);
}
