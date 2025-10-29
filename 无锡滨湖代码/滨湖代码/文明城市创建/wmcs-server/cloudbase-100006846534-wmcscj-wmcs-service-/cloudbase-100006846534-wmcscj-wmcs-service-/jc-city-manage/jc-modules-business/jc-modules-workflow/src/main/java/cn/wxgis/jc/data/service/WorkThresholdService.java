package cn.wxgis.jc.data.service;

import cn.wxgis.jc.civilization.quota.vo.EventTypeVo;
import cn.wxgis.jc.civilization.work.po.WorkThreshold;
import cn.wxgis.jc.common.web.service.BaseService;

public interface WorkThresholdService extends BaseService<WorkThreshold> {

    /**
     * 根据告警类型查询信息
     * @param warnType 告警类型
     */
    EventTypeVo findByWarnType(String warnType);
}
