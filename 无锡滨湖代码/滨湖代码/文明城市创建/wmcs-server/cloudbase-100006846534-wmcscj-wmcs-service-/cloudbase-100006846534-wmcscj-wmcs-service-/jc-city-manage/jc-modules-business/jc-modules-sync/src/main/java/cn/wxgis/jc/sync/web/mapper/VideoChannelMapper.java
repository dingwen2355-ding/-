package cn.wxgis.jc.sync.web.mapper;

import cn.wxgis.jc.civilization.data.po.DataDeviceChannel;
import cn.wxgis.jc.common.web.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VideoChannelMapper extends BaseDao<DataDeviceChannel> {
    /**
     * 删除数据
     * @return 删除条数
     */
    int deleteAll();
}
