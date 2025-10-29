package cn.wxgis.jc.data.service.impl;

import cn.wxgis.jc.common.web.service.impl.BaseServiceImpl;
import cn.wxgis.jc.data.mapper.WorkEventFileMapper;
import cn.wxgis.jc.data.service.WorkEventFileService;
import cn.wxgis.jc.synch.wmb.po.WorkEventFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class WorkEventFileServiceImpl extends BaseServiceImpl<WorkEventFileMapper, WorkEventFile> implements WorkEventFileService {
    public WorkEventFileServiceImpl(WorkEventFileMapper baseDao) {
        super(baseDao);
    }
}
