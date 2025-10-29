package cn.wxgis.jc.data.service.impl;

import cn.wxgis.jc.civilization.data.po.DataCheckItem;
import cn.wxgis.jc.common.web.service.impl.BaseServiceImpl;
import cn.wxgis.jc.data.mapper.DataCheckItemMapper;
import cn.wxgis.jc.data.service.DataCheckItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DataCheckItemServiceImpl extends BaseServiceImpl<DataCheckItemMapper, DataCheckItem> implements DataCheckItemService {
    public DataCheckItemServiceImpl(DataCheckItemMapper baseDao) {
        super(baseDao);
    }
}
