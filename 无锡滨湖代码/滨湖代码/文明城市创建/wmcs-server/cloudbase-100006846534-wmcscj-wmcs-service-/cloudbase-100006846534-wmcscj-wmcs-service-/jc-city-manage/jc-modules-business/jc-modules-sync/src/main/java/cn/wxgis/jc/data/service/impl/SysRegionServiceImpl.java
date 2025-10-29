package cn.wxgis.jc.data.service.impl;

import cn.wxgis.jc.common.web.service.impl.BaseServiceImpl;
import cn.wxgis.jc.data.mapper.SysRegionMapper;
import cn.wxgis.jc.data.service.SysRegionService;
import cn.wxgis.jc.system.po.SysRegion;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SysRegionServiceImpl extends BaseServiceImpl<SysRegionMapper, SysRegion> implements SysRegionService {
    public SysRegionServiceImpl(SysRegionMapper baseDao) {
        super(baseDao);
    }
}
