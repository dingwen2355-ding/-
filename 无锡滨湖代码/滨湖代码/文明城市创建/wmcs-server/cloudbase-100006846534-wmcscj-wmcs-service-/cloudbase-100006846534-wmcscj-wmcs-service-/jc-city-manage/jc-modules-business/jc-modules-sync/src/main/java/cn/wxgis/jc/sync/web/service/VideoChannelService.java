package cn.wxgis.jc.sync.web.service;

import cn.wxgis.jc.civilization.data.po.DataDeviceChannel;
import cn.wxgis.jc.common.web.service.BaseService;

public interface VideoChannelService extends BaseService<DataDeviceChannel> {

    /**
     * 删除数据
     * @return 删除条数
     */
    int deleteAll();

    boolean existsChannelId(String channelId);
}
