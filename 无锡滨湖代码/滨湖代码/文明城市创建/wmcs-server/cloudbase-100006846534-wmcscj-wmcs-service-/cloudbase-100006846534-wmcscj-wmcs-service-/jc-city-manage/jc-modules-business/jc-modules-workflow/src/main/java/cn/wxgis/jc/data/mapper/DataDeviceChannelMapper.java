package cn.wxgis.jc.data.mapper;

import cn.wxgis.jc.civilization.data.po.DataDeviceChannel;
import cn.wxgis.jc.civilization.data.resp.DataDeviceChannelResponse;
import cn.wxgis.jc.common.web.dao.BaseDao;
import cn.wxgis.jc.system.po.SysDept;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DataDeviceChannelMapper extends BaseDao<DataDeviceChannel> {

    /**
     * 根据区划编码查询部门ID
     * @param regionCode 区划编码
     */
    SysDept findDeptIdByRegionCode(@Param("regionCode") String regionCode);

    /**
     * 查询通道详情
     * @param channelId 通道ID
     */
    DataDeviceChannelResponse findResponseByChannelId(@Param("channelId") String channelId);
}
