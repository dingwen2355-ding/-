package cn.wxgis.jc.screen.web.service;

import cn.wxgis.jc.screen.domain.vo.points.DeviceChannelVo;
import cn.wxgis.jc.screen.domain.vo.points.RegionDeviceVo;
import cn.wxgis.jc.screen.domain.vo.points.TaskPointsVo;

import java.util.List;

public interface DeviceChannelService {

    /**
     * 查询视频设备列表
     * @return
     */
    List<DeviceChannelVo> getDeviceChannelList();

    /**
     * 根据区域名称查询数据
     * @param regionName 区域名称
     * @return 区域预案视频列表
     */
    List<RegionDeviceVo> getRegionDeviceList(String regionName);

    /**
     * 查询任务点位列表
     */
    List<TaskPointsVo> getTaskPointsList();
}
