package cn.wxgis.jc.data.service;

import cn.wxgis.jc.common.web.service.BaseService;
import cn.wxgis.jc.system.po.SysFile;

import java.util.List;

public interface SysFileService extends BaseService<SysFile> {

    /**
     * 根据id集合查询列表
     */
    List<SysFile> listByIds(List<String> ids);
}
