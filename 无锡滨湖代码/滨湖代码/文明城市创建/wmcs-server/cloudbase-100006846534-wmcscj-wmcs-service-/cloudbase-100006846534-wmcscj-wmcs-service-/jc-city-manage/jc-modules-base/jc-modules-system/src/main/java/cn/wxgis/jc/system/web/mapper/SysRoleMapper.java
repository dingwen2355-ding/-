package cn.wxgis.jc.system.web.mapper;

import cn.wxgis.jc.common.web.dao.BaseDao;
import cn.wxgis.jc.system.po.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysRoleMapper extends BaseDao<SysRole> {


    /**
     * 根据用户ID查询角色
     * @param userId 用户ID
     * @return 角色列表
     */
    List<SysRole> selectRolePermissionByUserId(@Param("userId") String userId);

}
