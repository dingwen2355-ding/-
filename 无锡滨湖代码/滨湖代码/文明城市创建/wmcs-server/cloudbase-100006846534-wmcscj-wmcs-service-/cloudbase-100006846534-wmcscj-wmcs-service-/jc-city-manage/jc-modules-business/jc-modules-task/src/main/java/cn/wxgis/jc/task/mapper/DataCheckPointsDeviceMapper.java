package cn.wxgis.jc.task.mapper;

import cn.wxgis.jc.civilization.data.po.DataCheckPointsDevice;
import cn.wxgis.jc.civilization.data.resp.DataCheckPointsResponse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DataCheckPointsDeviceMapper extends BaseMapper<DataCheckPointsDevice> {

    /**
     * 根据通道ID查询点位信息
     * @param channelId 通道ID
     */
    DataCheckPointsResponse findResponseByDeviceId(@Param("channelId") String channelId);
}
