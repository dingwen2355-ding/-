package cn.wxgis.jc.data.service;

import cn.wxgis.jc.civilization.quota.po.QuotaAssessCycle;
import cn.wxgis.jc.common.web.service.BaseService;

public interface QuotaAssessCycleService extends BaseService<QuotaAssessCycle> {

    /**
     * 获取最新一个考核周期
     */
    QuotaAssessCycle getNewData();
}
