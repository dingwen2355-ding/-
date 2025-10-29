package cn.wxgis.jc.data.service.impl;

import cn.wxgis.jc.common.web.service.impl.BaseServiceImpl;
import cn.wxgis.jc.data.mapper.SysDeptMapper;
import cn.wxgis.jc.data.service.SysDeptService;
import cn.wxgis.jc.system.po.SysDept;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class SysDeptServiceImpl extends BaseServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {
    public SysDeptServiceImpl(SysDeptMapper baseDao) {
        super(baseDao);
    }

    @Override
    public SysDept findByName(String name) {
        LambdaQueryWrapper<SysDept> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysDept::getName, name);
        return baseDao.selectOne(queryWrapper);
    }

    @Override
    public List<SysDept> listByParentId(String parentId) {
        LambdaQueryWrapper<SysDept> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(SysDept::getParentId, parentId);
        return baseDao.selectList(queryWrapper);
    }

    @Override
    public boolean findByWechatDeptId(String wechatDeptId) {
        LambdaQueryWrapper<SysDept> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysDept::getWechatDeptId, wechatDeptId);
        return baseDao.selectCount(queryWrapper) > 0;
    }
}
