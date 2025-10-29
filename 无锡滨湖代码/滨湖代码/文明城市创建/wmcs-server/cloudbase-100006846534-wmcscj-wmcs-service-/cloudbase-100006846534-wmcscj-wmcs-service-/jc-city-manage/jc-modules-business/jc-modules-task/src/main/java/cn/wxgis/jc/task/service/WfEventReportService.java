package cn.wxgis.jc.task.service;

import cn.wxgis.jc.common.web.service.BaseService;
import cn.wxgis.jc.workflow.po.WfEventReport;
import cn.wxgis.jc.workflow.vo.WmcsWarnVo;

import java.util.List;

public interface WfEventReportService extends BaseService<WfEventReport> {

    /**
     * 查询未办结的智能AI来源的数据
     */
    List<WfEventReport> listNoEndByAi();

    /**
     * 根据告警信息判断是否生成数据
     * @param warn
     */
    boolean existByWarnInfo(WmcsWarnVo warn);

}
