package cn.wxgis.jc.screen.web.mapper;

import cn.wxgis.jc.screen.domain.vo.count.EventCountVo;
import cn.wxgis.jc.screen.domain.vo.count.EventNameCountVo;
import cn.wxgis.jc.screen.domain.vo.count.EventRegionCountVo;
import cn.wxgis.jc.screen.domain.vo.count.EventRegionEndCountVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EventCountMapper {

    /**
     * 工单总览分析
     */
    List<EventCountVo> getEventStatus(@Param("regionName") String regionName);

    /**
     * 工单来源分析
     */
    List<EventCountVo> getEventSource(@Param("regionName") String regionName);

    /**
     * 高发工单类型
     */
    List<EventCountVo> getHighType(@Param("regionName") String regionName);

    /**
     * 工单趋势分析
     */
    List<EventNameCountVo> getEventTrend(@Param("regionName") String regionName, @Param("cycle") List<String> cycle);

    /**
     * 工单办结分析
     */
    List<EventNameCountVo> getEventEndList(@Param("regionName") String regionName, @Param("cycle") List<String> cycleList);

    /**
     * 高发点位分析
     */
    List<EventCountVo> getHighPoint(@Param("regionName") String regionName);

    /**
     * 查询各街镇事件数据
     */
    List<EventRegionCountVo> getEventRegionListByRegion(@Param("monthList") List<String> monthList);

    /**
     * 查询各职能部门事件数据
     * @param monthList
     * @return
     */
    List<EventRegionCountVo> getEventRegionListByDept(@Param("monthList") List<String> monthList);

    /**
     * 查询街镇工单总数、办结总数数据
     */
    List<EventRegionEndCountVo> getEventRegionEndList(@Param("cycle") String cycle);

    /**
     * 查询职能部门工单总数、办结总数数据
     */
    List<EventRegionEndCountVo> getEventDeptEndList(@Param("cycle") String cycle);


    /**
     * 查询事件总数
     */
    Integer getEventCount();


}
