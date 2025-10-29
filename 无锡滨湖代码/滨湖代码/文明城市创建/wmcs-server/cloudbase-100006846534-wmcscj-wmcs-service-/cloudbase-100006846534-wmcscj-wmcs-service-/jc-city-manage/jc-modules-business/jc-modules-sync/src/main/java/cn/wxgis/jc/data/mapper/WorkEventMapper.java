package cn.wxgis.jc.data.mapper;

import cn.wxgis.jc.common.web.dao.BaseDao;
import cn.wxgis.jc.synch.wmb.po.WorkEvent;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WorkEventMapper extends BaseDao<WorkEvent> {
}
