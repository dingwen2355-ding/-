package cn.wxgis.jc.system.web.service.impl;

import cn.wxgis.jc.common.constant.Constant;
import cn.wxgis.jc.common.domain.exists.ExistsParam;
import cn.wxgis.jc.common.domain.exists.ExistsReturn;
import cn.wxgis.jc.common.enums.common.DelFlagEnum;
import cn.wxgis.jc.common.enums.global.GlobalStatusEnum;
import cn.wxgis.jc.common.util.StringUtils;
import cn.wxgis.jc.common.util.id.IdUtils;
import cn.wxgis.jc.common.web.service.impl.BaseServiceImpl;
import cn.wxgis.jc.system.po.SysRole;
import cn.wxgis.jc.system.po.SysRoleMenu;
import cn.wxgis.jc.system.po.SysUser;
import cn.wxgis.jc.system.req.SysRoleSaveRequest;
import cn.wxgis.jc.system.select.SysRoleSelect;
import cn.wxgis.jc.system.web.mapper.SysRoleMapper;
import cn.wxgis.jc.system.web.mapper.SysRoleMenuMapper;
import cn.wxgis.jc.system.web.mapper.SysUserMapper;
import cn.wxgis.jc.system.web.service.SysRoleMenuService;
import cn.wxgis.jc.system.web.service.SysRoleService;
import cn.wxgis.jc.system.web.service.entity.SysRoleEntity;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
public class SysRoleServiceImpl extends BaseServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Autowired
    private SysRoleMenuService roleMenuService;
    @Autowired
    private SysUserMapper userMapper;
    public SysRoleServiceImpl(SysRoleMapper baseDao) {
        super(baseDao);
    }

    @Override
    @Transactional
    public int save(SysRoleSaveRequest request) {
        SysRole data = SysRoleEntity.getSaveRole(request);
        int result = validResult(baseDao.insert(data));
        /*** 保存菜单 ***/
        this.setMenuData(request.getMenus(), data.getId());
        return result;
    }

    @Override
    @Transactional
    public int edit(SysRoleSaveRequest request) {
        SysRole bean = validDataNull(baseDao.selectById(request.getId()));
        SysRole data = SysRoleEntity.getUpdateRole(request, bean);
        int result = validResult(baseDao.updateById(data));
        /*** 保存菜单 ***/
        this.setMenuData(request.getMenus(), data.getId());
        return result;
    }

    /**
     * 保存角色菜单
     * @param menus 菜单列表
     * @param roldId 角色id
     */
    private void setMenuData(List<String> menus, String roldId){
        if (StringUtils.isEmpty(menus)) return;
        // 根据角色id删除数据
        LambdaQueryWrapper<SysRoleMenu> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(SysRoleMenu::getRoleId, roldId);
        roleMenuService.delete(queryWrapper);
        // 保存新的菜单数据
        List<SysRoleMenu> roleMenuList = new ArrayList<>();
        menus.forEach(r -> {
            SysRoleMenu data = new SysRoleMenu();
            data.setRoleId(roldId);
            data.setMenuId(r);
            roleMenuList.add(data);
        });
        roleMenuService.insertBatch(roleMenuList);
    }

    @Override
    @Transactional
    public int delete(String id) {
        return baseDao.deleteById(id);
    }

    @Override
    public ExistsReturn exists(Map<String, String> existsParam) {
        ExistsReturn existsReturn = new ExistsReturn();
        boolean exists = false;
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRole::getName, existsParam.get(ExistsParam.name))
                .ne(StringUtils.isNotBlank(existsParam.get(ExistsParam.id)), SysRole::getId, existsParam.get(ExistsParam.id));
        List<SysRole> list = baseDao.selectList(queryWrapper);
        if (StringUtils.isNotEmpty(list)) {
            exists = true;
            existsReturn.setMessage(String.format("角色名称:%s", existsParam.get(ExistsParam.name)) + GlobalStatusEnum.DATA_EXISTE.getMsg());
        }
        return existsReturn.setExists(exists);
    }

    @Override
    public IPage<SysRole> page(SysRoleSelect select) {
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.like(StringUtils.isNotBlank(select.getName()), SysRole::getName, select.getName())
                .eq(StringUtils.isNotNull(select.getStatus()), SysRole::getStatus, select.getStatus())
                .eq(SysRole::getDelFlag, DelFlagEnum.DEFAULT.getCode());
        IPage<SysRole> iPage = baseDao.selectPage(getPage(select.getPageNum(), select.getPageSize(), Constant.ColumnField.ORDER_NUM, true), queryWrapper);
        return iPage;
    }

    @Override
    public List<SysRole> listByIds(List<String> ids) {
        if (StringUtils.isEmpty(ids) || ids.size() <= 0) return null;
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SysRole::getId, ids);
        return baseDao.selectList(queryWrapper);
    }

    @Override
    public Set<String> selectRolePermissionByUserId(String userId) {
        List<SysRole> perms = baseDao.selectRolePermissionByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (SysRole perm : perms) {
            if (StringUtils.isNotNull(perm)) {
                permsSet.addAll(Arrays.asList(perm.getCode().trim().split(",")));
            }
        }
        return permsSet;
    }

    @Override
    public boolean checkUserExistRole(String id) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(SysUser::getId, id);
        return userMapper.selectCount(queryWrapper) > 0;
    }

}
