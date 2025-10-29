package cn.wxgis.jc.data.service.impl;

import cn.wxgis.jc.common.enums.common.DelFlagEnum;
import cn.wxgis.jc.common.util.StringUtils;
import cn.wxgis.jc.common.web.service.impl.BaseServiceImpl;
import cn.wxgis.jc.data.mapper.SysUserMapper;
import cn.wxgis.jc.system.po.SysDept;
import cn.wxgis.jc.system.po.SysUser;
import cn.wxgis.jc.data.mapper.SysDeptMapper;
import cn.wxgis.jc.data.service.SysDeptService;
import cn.wxgis.jc.data.service.SysUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SysDeptServiceImpl extends BaseServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {

    @Autowired
    private SysUserMapper userMapper;
    public SysDeptServiceImpl(SysDeptMapper baseDao) {
        super(baseDao);
    }

    @Override
    public List<SysDept> listByIds(List<String> ids) {
        if (StringUtils.isEmpty(ids) || ids.size() <= 0) return new ArrayList<>();
        return baseDao.selectBatchIds(ids);
    }

    @Override
    public SysDept findByUserId(String userId) {
        SysUser user = userMapper.selectById(userId);
        if (StringUtils.isNull(user)) return null;
        String deptId = user.getDeptId();
        if (StringUtils.isBlank(deptId)) return null;
        return baseDao.selectById(deptId);
    }

    @Override
    public List<SysDept>  listByParentId(String parentId) {
        LambdaQueryWrapper<SysDept> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysDept::getParentId, parentId).eq(SysDept::getDelFlag, DelFlagEnum.DEFAULT.getCode());
        return baseDao.selectList(queryWrapper);
    }

    @Override
    public List<SysDept> getDeptIdsByUser(String handlerid) {
        List<String> handlerIds = Arrays.asList(handlerid.split(","));
        if (StringUtils.isEmpty(handlerIds) || handlerIds.size() <= 0) return null;
        List<SysUser> userList = userMapper.selectBatchIds(handlerIds);
        if (StringUtils.isEmpty(userList)) return null;
        List<String> deptIds = userList.stream().map(SysUser::getDeptId).collect(Collectors.toList());
        List<SysDept> deptList = baseDao.selectBatchIds(deptIds);
        return deptList;
    }

    @Override
    public SysDept findByName(String regionName) {
        LambdaQueryWrapper<SysDept> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysDept::getName, regionName);
        return selectOne(lambdaQueryWrapper);
    }

}
