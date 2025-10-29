package cn.wxgis.jc.task.service.impl;

import cn.wxgis.jc.civilization.work.po.WorkThreshold;
import cn.wxgis.jc.common.web.service.impl.BaseServiceImpl;
import cn.wxgis.jc.task.mapper.WorkThresholdMapper;
import cn.wxgis.jc.task.service.WorkThresholdService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class WorkThresholdServiceImpl extends BaseServiceImpl<WorkThresholdMapper, WorkThreshold> implements WorkThresholdService {
    public WorkThresholdServiceImpl(WorkThresholdMapper baseDao) {
        super(baseDao);
    }

    @Override
    public WorkThreshold findByType(String type) {
        LambdaQueryWrapper<WorkThreshold> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WorkThreshold::getAlarmType, type);
        return selectOne(queryWrapper);
    }
}
