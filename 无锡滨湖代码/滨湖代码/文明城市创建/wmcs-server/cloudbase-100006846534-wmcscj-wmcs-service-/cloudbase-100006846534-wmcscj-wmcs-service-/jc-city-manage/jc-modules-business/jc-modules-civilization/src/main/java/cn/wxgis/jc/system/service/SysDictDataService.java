package cn.wxgis.jc.system.service;

import cn.wxgis.jc.common.web.service.BaseService;
import cn.wxgis.jc.system.po.SysDictData;

import java.util.List;

public interface SysDictDataService extends BaseService<SysDictData> {
    List<SysDictData> listByDictType(String workAlarmType);
}
