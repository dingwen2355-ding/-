package cn.wxgis.jc.workflow.web.service;

import cn.wxgis.jc.workflow.web.select.EventCountSelect;
import cn.wxgis.jc.workflow.web.vo.EventCountVo;

import java.util.List;

public interface AnalysisService {

    /**
     * 查询统计分析列表
     */
    List<EventCountVo> eventCount(EventCountSelect select);
}
