package cn.wxgis.jc.screen.web.mapper;

import cn.wxgis.jc.screen.domain.vo.points.DeviceChannelVo;
import cn.wxgis.jc.screen.domain.vo.points.RegionDeviceVo;
import cn.wxgis.jc.screen.domain.vo.points.TaskPointsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DeviceChannelMapper {

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
    List<RegionDeviceVo> getRegionDeviceList(@Param("regionName") String regionName);

    /**
     * 查询任务点位
     * @return
     */
    List<TaskPointsVo> getTaskPointsList();
}
