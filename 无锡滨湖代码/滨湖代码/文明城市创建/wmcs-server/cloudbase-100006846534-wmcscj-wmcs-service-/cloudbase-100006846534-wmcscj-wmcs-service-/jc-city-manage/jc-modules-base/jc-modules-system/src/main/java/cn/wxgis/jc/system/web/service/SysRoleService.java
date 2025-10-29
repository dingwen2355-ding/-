package cn.wxgis.jc.system.web.service;

import cn.wxgis.jc.common.domain.exists.ExistsReturn;
import cn.wxgis.jc.common.web.service.BaseService;
import cn.wxgis.jc.system.po.SysRole;
import cn.wxgis.jc.system.req.SysRoleSaveRequest;
import cn.wxgis.jc.system.select.SysRoleSelect;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface SysRoleService extends BaseService<SysRole> {

    /**
     * 新增
     * @param request 角色信息
     * @return 新增条数
     */
    int save(SysRoleSaveRequest request);

    /**
     * 修改
     * @param request 修改信息
     * @return 修改条数
     */
    int edit(SysRoleSaveRequest request);

    /**
     * 删除
     * @param id 角色id
     * @return 删除条数
     */
    int delete(String id);


    /**
     * 验证唯一性
     * @param existsParam 验证条件
     * @return 结果
     */
    ExistsReturn exists(Map<String, String> existsParam);

    /**
     * 查询分页列表数据
     * @param select 查询条件
     * @return 角色分页列表
     */
    IPage<SysRole> page(SysRoleSelect select);

    /**
     * 根据id集合查询角色列表
     * @param roles 角色id集合
     * @return 角色列表
     */
    List<SysRole> listByIds(List<String> roles);

    /**
     * 根据用户ID查询角色权限
     * @param userId 用户ID
     * @return 权限列表
     */
    Set<String> selectRolePermissionByUserId(String userId);

    /**
     * 判度角色是否分配用户
     * @param id 角色id
     * @return 结果
     */
    boolean checkUserExistRole(String id);

}
