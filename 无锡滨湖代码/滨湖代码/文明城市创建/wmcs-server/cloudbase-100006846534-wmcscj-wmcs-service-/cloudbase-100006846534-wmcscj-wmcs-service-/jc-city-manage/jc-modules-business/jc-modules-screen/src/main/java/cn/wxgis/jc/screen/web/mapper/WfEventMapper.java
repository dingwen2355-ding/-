package cn.wxgis.jc.screen.web.mapper;

import cn.wxgis.jc.civilization.screen.EventSource;
import cn.wxgis.jc.common.util.page.QueryExt;
import cn.wxgis.jc.common.web.dao.BaseDao;
import cn.wxgis.jc.screen.domain.select.WfEventSelect;
import cn.wxgis.jc.screen.domain.vo.EventPointsAvgVo;
import cn.wxgis.jc.screen.domain.vo.EventTypeAvgVo;
import cn.wxgis.jc.screen.domain.vo.count.EventStatusCountVo;
import cn.wxgis.jc.screen.domain.vo.event.WfEventInfoVo;
import cn.wxgis.jc.screen.domain.vo.event.WfEventNodeVo;
import cn.wxgis.jc.screen.domain.vo.event.WfEventVo;
import cn.wxgis.jc.screen.domain.vo.event.WorkEventVo;
import cn.wxgis.jc.screen.domain.vo.map.EventHeatMapVo;
import cn.wxgis.jc.synch.wmb.po.WorkEventFile;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface WfEventMapper extends BaseDao {

    /**
     * 事件来源数
     */
    EventSource sourceCount();

    /**
     * 查询今日待办事件
     * @return 今日待办总数
     */
    Integer getTodoCountByToday();

    /**
     * 查询今日办结事件
     * @return 今日办结总数
     */
    Integer getFinishCountByToday();

    /**
     * 查询事件分页列表
     * @param queryExt
     * @param params
     * @return
     */
    IPage<WfEventVo> getEventPage(QueryExt queryExt, @Param("param") Map<String, Object> params);

    /**
     * 根据查询条件查询数据
     * @param select  查询条件
     * @return 事件列表
     */
    List<WfEventVo> getEventList(WfEventSelect select);

    /**
     * 根据事件id查询数据
     * @param eventId 事件id
     * @return 查询日常上报的事件详情
     */
    WfEventInfoVo getEventInfoByReport(@Param("eventId") String eventId);

    /**
     * 根据流程id查询数据
     * @param flowinstanceid 流程实例id
     * @return 日常巡查的节点列表
     */
    List<WfEventNodeVo> getEventNodeByReport(String flowinstanceid);

    /**
     * 根据事件id查询数据
     * @param eventId 事件id
     * @return 历史上报的事件详情
     */
    WfEventInfoVo getEventInfoByHistory(@Param("eventId") String eventId);

    /**
     * 根据事件id查询数据
     * @param eventId 事件id
     * @return 事件附件列表
     */
    List<WorkEventFile> getEventFilesByHistory(@Param("eventId") String eventId);

    /**
     * 查询工单热力图返回经纬度
     * @return 热力图经纬度列表
     */
    List<EventHeatMapVo> getEventHeatMap();

    /**
     * 查询事件各状态总数
     * @return  事件数统计
     */
    EventStatusCountVo getEventStatusCountVo();

    /**
     * 查询告警列表
     */
    List<WfEventVo> aiEventList(@Param("status") String status);

    /***
     * 事件点位整改率列表
     * @param regionName 区划名称
     * @param cycle 周期
     */
    List<EventPointsAvgVo> getEventPointsAvg(@Param("regionName") String regionName, @Param("cycle") String cycle);

    /**
     * 事件类型站比率
     * @param regionName
     * @param cycle
     */
    List<EventTypeAvgVo> getEventTypeAvg(@Param("regionName") String regionName, @Param("cycle") String cycle);

    /**
     * 获取事件列表
     */
    IPage<WorkEventVo> getWorkEvent(QueryExt queryExt, @Param("param") Map<String, Object> params);
}
