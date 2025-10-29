package cn.wxgis.jc.data.mapper;

import cn.wxgis.jc.common.web.dao.BaseDao;
import cn.wxgis.jc.system.po.SysBaseConfig;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysBaseConfigMapper extends BaseDao<SysBaseConfig> {
    SysBaseConfig getOne();

}
