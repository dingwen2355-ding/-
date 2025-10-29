package cn.wxgis.jc.system.web.mapper;

import cn.wxgis.jc.common.web.dao.BaseDao;
import cn.wxgis.jc.system.po.SysRegion;
import cn.wxgis.jc.system.resp.SysRegionResponse;
import cn.wxgis.jc.system.select.SysRegionSelect;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysRegionMapper extends BaseDao<SysRegion> {

    /**
     * 查询列表
     * @param select 查询条件
     * @return 区划列表
     */
    List<SysRegionResponse> voList(SysRegionSelect select);
}
