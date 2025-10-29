package cn.wxgis.jc.screen.web.service;

import cn.wxgis.jc.screen.domain.vo.count.EventCountVo;
import cn.wxgis.jc.screen.domain.vo.count.EventSourceCountVo;
import cn.wxgis.jc.screen.domain.vo.count.EventStatusCountVo;
import cn.wxgis.jc.screen.domain.vo.count.EventTrendCountVo;

import java.util.List;
import java.util.Map;

public interface EventCountService {

    /**
     * 工单总览分析
     * @param regionName
     * @return
     */
    EventStatusCountVo eventStatus(String regionName);

    /**
     * 工单来源分析
     * @param regionName
     * @return
     */
    EventSourceCountVo eventSource(String regionName);

    /**
     * 高发工单类型
     * @param regionName
     * @return
     */
    List<EventCountVo> highType(String regionName);

    /**
     * 工单趋势分析
     * @param regionName
     * @return
     */
    List<EventTrendCountVo> eventTrend(String regionName);

    /**
     * 工单办结分析
     * @param regionName
     * @return
     */
    Map<String, Map<String, Integer>> eventEnd(String regionName);

    /**
     * 高发点位分析
     * @param regionName
     * @return
     */
    List<EventCountVo> highPoint(String regionName);

    /**
     * 区域同环比分析
     * @param month
     * @return
     */
    Map<String, Map<String, Integer>> eventRatio(Integer type, String month);

    /**
     * 高发区域分析
     */
    Map<String, Map<String, Integer>> highRegion(Integer type, String cycle);
}
