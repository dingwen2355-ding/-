package cn.wxgis.jc.system.mapper;

import cn.wxgis.jc.common.web.dao.BaseDao;
import cn.wxgis.jc.system.po.SysDept;
import cn.wxgis.jc.system.resp.SysDeptResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysDeptMapper extends BaseDao<SysDept> {

    /**
     * 根据id获取部门信息
     */
    SysDeptResponse findResponseById(@Param("id") String id);

}
