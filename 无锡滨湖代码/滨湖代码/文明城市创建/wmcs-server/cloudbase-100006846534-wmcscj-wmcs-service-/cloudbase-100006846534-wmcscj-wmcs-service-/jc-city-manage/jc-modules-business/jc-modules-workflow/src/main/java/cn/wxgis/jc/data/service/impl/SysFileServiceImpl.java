package cn.wxgis.jc.data.service.impl;

import cn.wxgis.jc.common.util.StringUtils;
import cn.wxgis.jc.common.web.service.impl.BaseServiceImpl;
import cn.wxgis.jc.data.mapper.SysFileMapper;
import cn.wxgis.jc.data.service.SysFileService;
import cn.wxgis.jc.system.po.SysFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class SysFileServiceImpl extends BaseServiceImpl<SysFileMapper, SysFile> implements SysFileService {
    public SysFileServiceImpl(SysFileMapper baseDao) {
        super(baseDao);
    }

    @Override
    public List<SysFile> listByIds(List<String> ids) {
        if (StringUtils.isEmpty(ids) || ids.size() <= 0) return null;
        return baseDao.selectBatchIds(ids);
    }
}
