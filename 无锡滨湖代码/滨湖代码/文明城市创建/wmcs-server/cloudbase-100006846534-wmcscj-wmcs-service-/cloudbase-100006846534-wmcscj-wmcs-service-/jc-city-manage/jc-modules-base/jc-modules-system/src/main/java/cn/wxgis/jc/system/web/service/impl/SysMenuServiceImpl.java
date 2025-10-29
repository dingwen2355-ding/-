package cn.wxgis.jc.system.web.service.impl;

import cn.wxgis.jc.common.constant.Constant;
import cn.wxgis.jc.common.domain.exists.ExistsParam;
import cn.wxgis.jc.common.domain.exists.ExistsReturn;
import cn.wxgis.jc.common.enums.common.DelFlagEnum;
import cn.wxgis.jc.common.enums.global.GlobalStatusEnum;
import cn.wxgis.jc.common.result.TreeNode;
import cn.wxgis.jc.common.util.StringUtils;
import cn.wxgis.jc.common.web.service.impl.BaseServiceImpl;
import cn.wxgis.jc.system.po.SysDept;
import cn.wxgis.jc.system.po.SysMenu;
import cn.wxgis.jc.system.po.SysRoleMenu;
import cn.wxgis.jc.system.req.SysMenuSaveRequest;
import cn.wxgis.jc.system.resp.LoginMenuResponse;
import cn.wxgis.jc.system.resp.SysMenuResponse;
import cn.wxgis.jc.system.select.SysMenuSelect;
import cn.wxgis.jc.system.web.mapper.SysMenuMapper;
import cn.wxgis.jc.system.web.mapper.SysRoleMenuMapper;
import cn.wxgis.jc.system.web.service.SysMenuService;
import cn.wxgis.jc.system.web.service.entity.SysMenuEntity;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SysMenuServiceImpl extends BaseServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Autowired
    private SysRoleMenuMapper roleMenuMapper;
    public SysMenuServiceImpl(SysMenuMapper baseDao) {
        super(baseDao);
    }

    @Override
    @Transactional
    public int save(SysMenuSaveRequest request) {
        SysMenu data = SysMenuEntity.getSaveMenu(request);
        this.setParentMenu(data, request.getParentId());
        return validResult(baseDao.insert(data));
    }

    /**
     * 设置父级菜单
     * @param menu
     * @param parentId
     */
    private void setParentMenu(SysMenu menu, String parentId) {
        if(StringUtils.isNotEmpty(parentId) && !"0".equals(parentId)) {
            SysMenu parentMenu = baseDao.selectById(parentId);
            parentMenu.setParentId(parentId);
            parentMenu.setType(menu.getType());
        } else {
            menu.setParentId("0");
        }
    }

    @Override
    @Transactional
    public int edit(SysMenuSaveRequest request) {
        SysMenu bean = validDataNull(baseDao.selectById(request.getId()));
        SysMenu data = SysMenuEntity.getUpdateMenu(request, bean);
        this.setParentMenu(data, request.getParentId());
        return validResult(baseDao.updateById(data));
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
        LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysMenu::getName, existsParam.get(ExistsParam.name))
                .eq(StringUtils.isNotBlank(existsParam.get(ExistsParam.parentId)), SysMenu::getParentId, existsParam.get(ExistsParam.parentId))
                .eq(SysMenu::getPlatform, existsParam.get(ExistsParam.platform))
                .ne(StringUtils.isNotBlank(existsParam.get(ExistsParam.id)), SysMenu::getId, existsParam.get(ExistsParam.id));
        List<SysMenu> list = baseDao.selectList(queryWrapper);
        if (StringUtils.isNotEmpty(list)) {
            exists = true;
            existsReturn.setMessage(String.format("菜单名称:%s", existsParam.get(ExistsParam.name)) + GlobalStatusEnum.DATA_EXISTE.getMsg());
        }
        return existsReturn.setExists(exists);
    }

    @Override
    public List<SysMenuResponse> listMenuTree(SysMenuSelect select) {
        List<SysMenuResponse> volist = baseDao.voList(select);
        TreeNode<SysMenuResponse> tree = new TreeNode<>(volist);
        List<SysMenuResponse> resultList = tree.buildThree();
        return resultList;
    }

    @Override
    public SysMenuResponse findResponseById(String id) {
        SysMenuResponse menuResponse = new SysMenuResponse();
        SysMenu menu = baseDao.selectById(id);
        if (StringUtils.isNull(menu)) return null;
        BeanUtils.copyProperties(menu, menuResponse);
        LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysMenu::getParentId, id).eq(SysMenu::getDelFlag, DelFlagEnum.DEFAULT.getCode());
        SysMenu parentMenu = baseDao.selectOne(queryWrapper);
        menuResponse.setParentName(StringUtils.isNotNull(parentMenu) ? parentMenu.getName() : null);
        return menuResponse;
    }

    @Override
    public LoginMenuResponse getMenuTreeByUserId(String userId, Integer platform) {
        LoginMenuResponse loginMenu = new LoginMenuResponse();
        if (Constant.DEFAULT_USER_ID.equals(userId)) {
            // 如果为管理员，返回所有菜单
            List<SysMenuResponse> menuList = baseDao.getListByMenu(platform);
            TreeNode<SysMenuResponse> menuTree = new TreeNode<>(menuList);
            loginMenu.setMenuList(menuTree.buildThree());

            List<SysMenuResponse> functionList = baseDao.getListByFunction(platform);
            if (StringUtils.isNotEmpty(functionList)) {
                loginMenu.setFunctionList(functionList.stream().map(SysMenuResponse::getPerms).collect(Collectors.toList()));
            }
        } else {
            //如果不为管理员，根据角色返回菜单
            List<SysMenuResponse> menuList = baseDao.getMenuTreeByUserId(userId, platform);
            TreeNode<SysMenuResponse> menuTree = new TreeNode<>(menuList);
            loginMenu.setMenuList(menuTree.buildThree());
            //如果不为管理员，根据角色返回菜单
            List<SysMenuResponse> functionList = baseDao.getFunctionTreeByUserId(userId, platform);
            if (StringUtils.isNotEmpty(functionList)) {
                loginMenu.setFunctionList(functionList.stream().map(SysMenuResponse::getPerms).collect(Collectors.toList()));
            }
        }
        return loginMenu;
    }

    @Override
    public Set<String> selectMenuPermsByRoleId(String roleId) {
        List<String> perms = baseDao.selectMenuPermsByRoleId(roleId);
        Set<String> permsSet = new HashSet<>();
        for (String perm : perms) {
            if (StringUtils.isNotEmpty(perm)) {
                permsSet.addAll(Arrays.asList(perm.trim().split(",")));
            }
        }
        return permsSet;
    }

    @Override
    public Set<String> selectMenuPermsByUserId(String userId) {
        List<String> perms = baseDao.selectMenuPermsByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (String perm : perms) {
            if (StringUtils.isNotEmpty(perm)) {
                permsSet.addAll(Arrays.asList(perm.trim().split(",")));
            }
        }
        return permsSet;
    }

    @Override
    public boolean hasChildByMenuId(String id) {
        LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysMenu::getParentId, id);
        return baseDao.selectCount(queryWrapper) > 0;
    }

    @Override
    public boolean checkMenuExistRole(String id) {
        LambdaQueryWrapper<SysRoleMenu> rolequeryWrapper = new LambdaQueryWrapper<>();
        rolequeryWrapper.eq(SysRoleMenu::getMenuId, id);
        return roleMenuMapper.selectCount(rolequeryWrapper) > 0;
    }
}
