package cn.wxgis.jc.system.web.service;

import cn.wxgis.jc.common.web.service.BaseService;
import cn.wxgis.jc.system.po.SysOperLog;
import cn.wxgis.jc.system.select.SysOperLogSelect;
import com.baomidou.mybatisplus.core.metadata.IPage;

public interface SysOperlogService extends BaseService<SysOperLog> {

    /**
     * 新增
     * @param operLog 操作信息
     * @return 新增条数
     */
    int save(SysOperLog operLog);

    /**
     * 查询分页列表
     * @param select 查询参数
     * @return 操作日志列表
     */
    IPage<SysOperLog> page(SysOperLogSelect select);
}
