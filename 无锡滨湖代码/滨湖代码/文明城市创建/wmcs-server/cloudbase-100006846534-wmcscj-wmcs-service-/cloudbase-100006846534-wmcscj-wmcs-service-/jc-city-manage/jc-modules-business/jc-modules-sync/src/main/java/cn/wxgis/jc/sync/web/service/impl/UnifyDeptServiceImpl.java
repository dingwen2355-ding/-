package cn.wxgis.jc.sync.web.service.impl;

import cn.wxgis.jc.common.web.service.impl.BaseServiceImpl;
import cn.wxgis.jc.sync.web.mapper.UnifyDeptMapper;
import cn.wxgis.jc.sync.web.service.UnifyDeptService;
import cn.wxgis.jc.synch.unify.po.UnifyDept;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UnifyDeptServiceImpl extends BaseServiceImpl<UnifyDeptMapper, UnifyDept> implements UnifyDeptService {
    public UnifyDeptServiceImpl(UnifyDeptMapper baseDao) {
        super(baseDao);
    }

    @Override
    public List<UnifyDept> listByParentId(String parentId) {
        LambdaQueryWrapper<UnifyDept> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UnifyDept::getParentId, parentId);
        return baseDao.selectList(queryWrapper);
    }
}
