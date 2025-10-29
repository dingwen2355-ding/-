package cn.wxgis.jc.data.service.impl;

import cn.wxgis.jc.common.constant.ConstantForError;
import cn.wxgis.jc.common.enums.common.DelFlagEnum;
import cn.wxgis.jc.common.enums.system.DeptTypeEnum;
import cn.wxgis.jc.common.exception.ServiceException;
import cn.wxgis.jc.common.util.StringUtils;
import cn.wxgis.jc.common.web.service.impl.BaseServiceImpl;
import cn.wxgis.jc.system.po.SysDept;
import cn.wxgis.jc.system.po.SysUser;
import cn.wxgis.jc.data.mapper.SysUserMapper;
import cn.wxgis.jc.data.service.SysDeptService;
import cn.wxgis.jc.data.service.SysUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysDeptService deptService;
    public SysUserServiceImpl(SysUserMapper baseDao) {
        super(baseDao);
    }

    @Override
    public List<SysUser> userListByDeptIds(List<String> deptIds) {
        List<SysUser> resultList = new ArrayList<>();
        if (StringUtils.isEmpty(deptIds) || deptIds.size() <= 0) return null;
        deptIds.forEach(i -> {
            LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SysUser::getDeptId, i);
            List<SysUser> userList = baseDao.selectList(queryWrapper);
            resultList.addAll(userList);
        });
        return resultList;
    }

    @Override
    public List<SysUser> listByRegionDeptIds(List<String> deptIds) {
        List<SysUser> resultList = new ArrayList<>();
        if (StringUtils.isEmpty(deptIds) || deptIds.size() <= 0) return null;
        List<SysDept> deptList = deptService.listByIds(deptIds);
        List<SysDept> tempDept = deptList.stream().filter(i -> i.getType().equals(DeptTypeEnum.REGION.getCode())).collect(Collectors.toList());
        List<String> params = tempDept.stream().map(SysDept::getId).collect(Collectors.toList());
        if (StringUtils.isEmpty(params) || params.size() <= 0) return null;
        deptIds.forEach(i -> {
            LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(SysUser::getDeptId, deptIds);
            List<SysUser> userList = baseDao.selectList(queryWrapper);
            if (StringUtils.isEmpty(userList)) throw new ServiceException(String.format(ConstantForError.NOT_HANDLEUSER, "部门") );
            resultList.addAll(userList);
        });
        return resultList;
    }

    @Override
    public List<SysUser> listByFunDeptIds(List<String> deptIds) {
        List<SysUser> resultList = new ArrayList<>();
        if (StringUtils.isEmpty(deptIds) || deptIds.size() <= 0) return null;
        List<SysDept> deptList = deptService.listByIds(deptIds);
        List<SysDept> tempDept = deptList.stream().filter(i -> i.getType().equals(DeptTypeEnum.DEPT.getCode())).collect(Collectors.toList());
        List<String> params = tempDept.stream().map(SysDept::getId).collect(Collectors.toList());
        if (StringUtils.isEmpty(params) || params.size() <= 0) return null;
        deptIds.forEach(i -> {
            LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(SysUser::getDeptId, deptIds);
            List<SysUser> userList = baseDao.selectList(queryWrapper);
            if (StringUtils.isEmpty(userList)) throw new ServiceException(String.format(ConstantForError.NOT_HANDLEUSER, "部门") );
            resultList.addAll(userList);
        });
        return resultList;
    }

    @Override
    public List<SysUser> listByDeptId(String deptId) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getDelFlag, DelFlagEnum.DEFAULT.getCode()).eq(SysUser::getDeptId, deptId);
        return baseDao.selectList(queryWrapper);
    }

    @Override
    public List<SysUser> userListByMyDept(String deptId) {
        // 查询部门id的部门列表
        List<SysDept> deptList = deptService.listByParentId(deptId);
        if (StringUtils.isEmpty(deptList)) return null;
        List<String> deptIds = deptList.stream().map(SysDept::getId).collect(Collectors.toList());
        List<SysUser> userList = this.userListByDeptIds(deptIds);
        if (StringUtils.isEmpty(userList)) throw new ServiceException(String.format(ConstantForError.NOT_HANDLEUSER, "部门") );
        return userList;
    }


}
