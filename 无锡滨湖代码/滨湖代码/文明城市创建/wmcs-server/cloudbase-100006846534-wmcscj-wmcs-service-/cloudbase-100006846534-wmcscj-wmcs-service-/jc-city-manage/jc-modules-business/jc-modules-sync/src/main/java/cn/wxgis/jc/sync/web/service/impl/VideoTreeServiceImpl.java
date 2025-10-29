package cn.wxgis.jc.sync.web.service.impl;

import cn.wxgis.jc.civilization.data.po.DataDeviceTree;
import cn.wxgis.jc.common.web.service.impl.BaseServiceImpl;
import cn.wxgis.jc.sync.web.mapper.VideoTreeMapper;
import cn.wxgis.jc.sync.web.service.VideoTreeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class VideoTreeServiceImpl extends BaseServiceImpl<VideoTreeMapper, DataDeviceTree> implements VideoTreeService {
    public VideoTreeServiceImpl(VideoTreeMapper baseDao) {
        super(baseDao);
    }

    @Transactional
    @Override
    public int deleteAll() {
        return baseDao.deleteAll();
    }

}
