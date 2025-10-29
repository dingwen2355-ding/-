package cn.wxgis.jc.file.web.service;

import cn.wxgis.jc.common.web.service.BaseService;
import cn.wxgis.jc.system.po.SysFile;

import java.util.List;

public interface SysFileService extends BaseService<SysFile> {

    /**
     * 根据id集合查询文件列表
     * @param ids 文件id集合
     * @return 文件列表
     */
    List<SysFile> listByIds(List<String> ids);
}
