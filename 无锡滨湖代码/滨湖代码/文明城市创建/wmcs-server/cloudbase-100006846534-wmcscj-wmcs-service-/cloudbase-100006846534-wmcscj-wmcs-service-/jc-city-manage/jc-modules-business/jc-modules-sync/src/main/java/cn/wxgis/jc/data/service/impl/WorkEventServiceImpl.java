package cn.wxgis.jc.data.service.impl;

import cn.wxgis.jc.common.web.service.impl.BaseServiceImpl;
import cn.wxgis.jc.data.mapper.WorkEventMapper;
import cn.wxgis.jc.data.service.WorkEventService;
import cn.wxgis.jc.synch.wmb.po.WorkEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class WorkEventServiceImpl extends BaseServiceImpl<WorkEventMapper, WorkEvent>implements WorkEventService {
    public WorkEventServiceImpl(WorkEventMapper baseDao) {
        super(baseDao);
    }
}
