package cn.wxgis.jc.system.web.service;

import cn.wxgis.jc.common.web.service.BaseService;
import cn.wxgis.jc.system.po.SysLogininfor;
import cn.wxgis.jc.system.select.SysLogininforSelect;
import com.baomidou.mybatisplus.core.metadata.IPage;

public interface SysLogininforService extends BaseService<SysLogininfor> {

    /**
     * 新增
     * @param logininfor 系统日志信息
     * @return 新增条数
     */
    int save(SysLogininfor logininfor);

    /**
     *
     * @param select
     * @return
     */
    IPage<SysLogininfor> page(SysLogininforSelect select);
}
