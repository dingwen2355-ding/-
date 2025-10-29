package cn.wxgis.jc.sync.web.service;

import cn.wxgis.jc.civilization.data.po.DataDeviceTree;
import cn.wxgis.jc.common.web.service.BaseService;

public interface VideoTreeService extends BaseService<DataDeviceTree> {

    /**
     * 删除所有数据
     * @return 删除条数
     */
    int deleteAll();

}
