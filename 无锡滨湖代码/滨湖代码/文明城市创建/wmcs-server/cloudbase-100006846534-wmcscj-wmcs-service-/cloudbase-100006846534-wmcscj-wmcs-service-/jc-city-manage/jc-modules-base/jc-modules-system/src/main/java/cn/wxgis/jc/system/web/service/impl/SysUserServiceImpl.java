package cn.wxgis.jc.system.web.service.impl;

import cn.wxgis.jc.common.constant.Constant;
import cn.wxgis.jc.common.domain.exists.ExistsParam;
import cn.wxgis.jc.common.domain.exists.ExistsReturn;
import cn.wxgis.jc.common.enums.common.DelFlagEnum;
import cn.wxgis.jc.common.enums.global.GlobalStatusEnum;
import cn.wxgis.jc.common.enums.system.UserStatusEnum;
import cn.wxgis.jc.common.exception.ServiceException;
import cn.wxgis.jc.common.result.JsonResult;
import cn.wxgis.jc.common.util.StringUtils;
import cn.wxgis.jc.common.web.service.impl.BaseServiceImpl;
import cn.wxgis.jc.security.annotation.DataScope;
import cn.wxgis.jc.security.utils.SecurityUtils;
import cn.wxgis.jc.synch.unify.po.UnifyUser;
import cn.wxgis.jc.system.model.LoginUser;
import cn.wxgis.jc.system.po.SysDept;
import cn.wxgis.jc.system.po.SysRole;
import cn.wxgis.jc.system.po.SysUser;
import cn.wxgis.jc.system.req.SysUserSaveRequest;
import cn.wxgis.jc.system.resp.SysDeptResponse;
import cn.wxgis.jc.system.resp.SysUserResponse;
import cn.wxgis.jc.system.select.SysUserSelect;
import cn.wxgis.jc.system.web.mapper.SysDeptMapper;
import cn.wxgis.jc.system.web.mapper.SysUserMapper;
import cn.wxgis.jc.system.web.service.PermissionService;
import cn.wxgis.jc.system.web.service.SysDeptService;
import cn.wxgis.jc.system.web.service.SysUserRoleService;
import cn.wxgis.jc.system.web.service.SysUserService;
import cn.wxgis.jc.system.web.service.entity.SysUserEntity;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysUserRoleService userRoleService;
    @Autowired
    private SysDeptService deptService;
    @Autowired
    private PermissionService permissionService;
    public SysUserServiceImpl(SysUserMapper baseDao) {
        super(baseDao);
    }

    @Override
    @Transactional
    public int save(SysUserSaveRequest request) {
        SysUser data = SysUserEntity.getSaveUser(request);
        int result = validResult(baseDao.insert(data));
        /*** 保存用户角色关联信息 ***/
        userRoleService.saveByUser(data.getId(), request.getRoles());
        return result;
    }

    @Override
    @Transactional
    public int edit(SysUserSaveRequest request) {
        SysUser bean = validDataNull(baseDao.selectById(request.getId()));
        SysUser data = SysUserEntity.getUpdateUser(request, bean);
        int result = validResult(baseDao.updateById(data));
        /*** 保存用户角色关联信息 ***/
        userRoleService.saveByUser(data.getId(), request.getRoles());
        return result;
    }

    @Override
    @Transactional
    public int delete(String id) {
        SysUser data = validDataNull(baseDao.selectById(id));
        data.setStatus(UserStatusEnum.DELETED.getCode());
        data.setDelFlag(DelFlagEnum.DELETE.getCode());
        return validResult(baseDao.updateById(data));
    }

    @Override
    @Transactional
    public int updateMyPassword(SysUser user, String password) {
        String newPassword = SecurityUtils.encryptPassword(password);
        user.setPassword(newPassword);
        return validResult(baseDao.updateById(user));
    }

    @DataScope(deptAlias = "t")
    @Override
    public IPage<SysUserResponse> page(SysUserSelect select) {
        LambdaQueryWrapper<SysUserResponse> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.like(StringUtils.isNotBlank(select.getUserName()), SysUserResponse::getUserName, select.getUserName())
                .like(StringUtils.isNotBlank(select.getNickName()), SysUserResponse::getNickName, select.getNickName())
                .like(StringUtils.isNotBlank(select.getPhonenumber()), SysUserResponse::getPhonenumber, select.getPhonenumber())
                .eq(StringUtils.isNotBlank(select.getDeptId()), SysUserResponse::getDeptId, select.getDeptId())
                .eq(StringUtils.isNotNull(select.getStatus()), SysUserResponse::getStatus, select.getStatus())
                .eq(SysUserResponse::getDelFlag, DelFlagEnum.DEFAULT.getCode());
        IPage<SysUserResponse> responseIPage = baseDao.selectPageCustom(getPage(select.getPageNum(), select.getPageSize(), select.getOrderField(), select.isOrderAsc()), queryWrapper, select.getParams());
        if (StringUtils.isEmpty(responseIPage.getRecords())) return responseIPage;
        List<SysUserResponse> list = responseIPage.getRecords();
        list.forEach(i -> {
            if (StringUtils.isNotBlank(i.getDeptId()) && select.getPageSize() <= Constant.DEFAULT_MAX_PAGE_SIZE) {
                SysDeptResponse deptResponse = deptService.getAllNameByDept(i.getDeptId());
                i.setDeptName(StringUtils.isNotNull(deptResponse) ? deptResponse.getAllname() : null);
            }
            i.setRoleNames(userRoleService.getRoleNamesByUserId(i.getId()));
            i.setPassword(Constant.HIDDEN);
        });
        return responseIPage;
    }

    @Override
    public SysUserResponse findResponseById(String id) {
        SysUserResponse response = new SysUserResponse();
        SysUser user = baseDao.selectById(id);
        BeanUtils.copyProperties(user, response);
        // 查询用户所属部门
        SysDept dept = deptService.selectById(user.getDeptId());
        response.setDeptName(StringUtils.isNotNull(dept) ? dept.getName() : "无");
        // 查询用户关联部门
        List<SysRole> roles = userRoleService.selectRoleListByUserId(user.getId());
        if (StringUtils.isNotEmpty(roles) && StringUtils.isNotNull(roles.get(0))) {
            response.setRoles(roles.stream().map(SysRole::getId).toArray(String[]::new));
        }
        response.setPassword(Constant.HIDDEN);
        return response;
    }

    @Override
    public ExistsReturn exists(Map<String, String> existsParam) {
        ExistsReturn existsReturn = new ExistsReturn();
        boolean exists = false;
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getUserName, existsParam.get(ExistsParam.userName))
                .eq(SysUser::getPhonenumber, existsParam.get(ExistsParam.phonenumber))
                .ne(StringUtils.isNotBlank(existsParam.get(ExistsParam.id)), SysUser::getId, existsParam.get(ExistsParam.id));
        List<SysUser> list = baseDao.selectList(queryWrapper);
        if (StringUtils.isNotEmpty(list)) {
            exists = true;
            existsReturn.setMessage(String.format("用户名称:%s", existsParam.get(ExistsParam.userName)) + String.format("手机号码:%s", existsParam.get(ExistsParam.phonenumber))  + GlobalStatusEnum.DATA_EXISTE.getMsg());
        }
        return existsReturn.setExists(exists);
    }

    @Override
    public SysUser findByUserName(String userName) {
        LambdaQueryWrapper<SysUser> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getUserName, userName);
        SysUser user = baseDao.selectOne(queryWrapper);
        return user;
    }

    @Override
    public LoginUser getLoginUser(String userName) {
        SysUser user = this.findByUserName(userName);
//        if (StringUtils.isNull(user)) throw new ServiceException(GlobalStatusEnum.PASSWORD_MISTAKE_EXCEPTION.getMsg());
        if (StringUtils.isNull(user)) return null;
        LoginUser loginUser = new LoginUser();
        loginUser.setUser(user);
        // 部门
        loginUser.setDept(deptService.selectById(user.getDeptId()));
        // 权限
        Set<String> permissions = permissionService.getMenuPermission(user.getId());
        loginUser.setPermissions(permissions);
        loginUser.setName(user.getNickName());
        // 角色
        if (!user.getId().equals(Constant.DEFAULT_USER_ID)) {
            List<SysRole> roleList = userRoleService.selectRoleListByUserId(user.getId());
            if (StringUtils.isNotEmpty(roleList)) {
                loginUser.setRoles(roleList.stream().map(SysRole::getId).toArray(String[]::new));
                loginUser.setRoleNames(roleList.stream().map(SysRole::getName).toArray(String[]::new));
                loginUser.setRoleList(roleList);
            }
            Set<String> roles = permissionService.getRolePermission(SecurityUtils.getUserId());
            loginUser.setUserroles(roles);
            loginUser.setDeptId(StringUtils.isNotNull(loginUser.getDept()) ? loginUser.getDept().getId() : null);
            loginUser.setDeptName(StringUtils.isNotNull(loginUser.getDept()) ? loginUser.getDept().getName() : null);
        }
        return loginUser;
    }

    @Override
    public UnifyUser findUnifyUserByUsername(String username) {
        return baseDao.findUnifyUserByUsername(username);
    }

}
