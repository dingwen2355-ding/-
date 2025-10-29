package cn.wxgis.jc.screen.web.service;

import cn.wxgis.jc.civilization.screen.EventSource;
import cn.wxgis.jc.screen.domain.select.WfEventListSelect;
import cn.wxgis.jc.screen.domain.select.WfEventSelect;
import cn.wxgis.jc.screen.domain.vo.EventPointsAvgVo;
import cn.wxgis.jc.screen.domain.vo.EventTypeAvgVo;
import cn.wxgis.jc.screen.domain.vo.count.EventStatusCountVo;
import cn.wxgis.jc.screen.domain.vo.count.TodayVo;
import cn.wxgis.jc.screen.domain.vo.event.WfEventInfoVo;
import cn.wxgis.jc.screen.domain.vo.event.WfEventVo;
import cn.wxgis.jc.screen.domain.vo.event.WorkEventVo;
import cn.wxgis.jc.screen.domain.vo.map.EventHeatMapVo;
import cn.wxgis.jc.synch.wmb.po.WorkEvent;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

public interface WfEventService {

    /**
     * 事件来源数
     */
    EventSource sourceCount();

    /***
     * 查询今日待办、办结事件
     * @return
     */
    TodayVo todayEvent();

    /**
     * 查询事件列表
     * @param select
     * @return
     */
    IPage<WfEventVo> getEventList(WfEventSelect select);

    /**
     * 查询事件详情
     * @param eventId 事件id
     * @param eventSource 事件来源
     * @return 事件信息
     */
    WfEventInfoVo getEventInfo(String eventId, Integer eventSource);

    /**
     * 查询工单热力图返回经纬度
     * @return 事件热力图列表
     */
    List<EventHeatMapVo> getEventHeatMap();

    /**
     * 查询事件总数
     * @return 事件总数
     */
    EventStatusCountVo getEventStatusCount();

    /**
     * 获取AI事件列表
     */
    List<WfEventVo> aiEventList(String status);

    /**
     * 获取点位事件政府该率
     * @param regionName
     * @param cycle
     */
    List<EventPointsAvgVo> getEventPointsAvg(String regionName, String cycle);

    /**
     * 事件类型站比率
     * @param regionName
     * @param cycle
     */
    List<EventTypeAvgVo> getEventTypeAvg(String regionName, String cycle);

    /**'
     * 获取事件列表
     */
    IPage<WorkEventVo> getWorkEvent(WfEventListSelect select);

}
