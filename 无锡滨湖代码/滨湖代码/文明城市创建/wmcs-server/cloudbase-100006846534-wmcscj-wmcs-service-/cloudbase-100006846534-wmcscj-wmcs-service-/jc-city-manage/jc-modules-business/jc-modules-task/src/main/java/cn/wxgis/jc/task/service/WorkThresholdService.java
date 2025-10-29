package cn.wxgis.jc.task.service;

import cn.wxgis.jc.civilization.work.po.WorkThreshold;
import cn.wxgis.jc.common.web.service.BaseService;

public interface WorkThresholdService extends BaseService<WorkThreshold> {

    /**
     * 根据类型查询数据
     */
    WorkThreshold findByType(String type);

}
