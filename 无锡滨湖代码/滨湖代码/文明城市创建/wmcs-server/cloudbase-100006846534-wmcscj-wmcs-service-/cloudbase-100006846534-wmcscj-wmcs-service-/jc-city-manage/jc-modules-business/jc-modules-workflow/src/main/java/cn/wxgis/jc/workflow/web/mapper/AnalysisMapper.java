package cn.wxgis.jc.workflow.web.mapper;

import cn.wxgis.jc.workflow.web.select.EventCountSelect;
import cn.wxgis.jc.workflow.web.vo.EventCountVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AnalysisMapper {

    /**
     * 查询统计分析列表
     */
    List<EventCountVo> eventCount(EventCountSelect select);

    /**
     * 办结列表
     */
    List<EventCountVo> eventFinishCount(EventCountSelect select);

    /**
     * 超时列表
     */
    List<EventCountVo> eventTimeoutCount(EventCountSelect select);
}
