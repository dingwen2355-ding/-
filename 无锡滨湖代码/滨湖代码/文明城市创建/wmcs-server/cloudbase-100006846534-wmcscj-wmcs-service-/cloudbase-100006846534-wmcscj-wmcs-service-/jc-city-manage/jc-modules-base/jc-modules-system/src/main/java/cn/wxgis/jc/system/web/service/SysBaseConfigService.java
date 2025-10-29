package cn.wxgis.jc.system.web.service;

import cn.wxgis.jc.common.web.service.BaseService;
import cn.wxgis.jc.system.po.SysBaseConfig;
import cn.wxgis.jc.system.req.SysBaseConfigSaveRequest;

public interface SysBaseConfigService extends BaseService<SysBaseConfig> {


    /**
     * 新增
     */
    int save(SysBaseConfigSaveRequest request);

    /**
     * 系统配置信息
     */
    SysBaseConfig info();

}
