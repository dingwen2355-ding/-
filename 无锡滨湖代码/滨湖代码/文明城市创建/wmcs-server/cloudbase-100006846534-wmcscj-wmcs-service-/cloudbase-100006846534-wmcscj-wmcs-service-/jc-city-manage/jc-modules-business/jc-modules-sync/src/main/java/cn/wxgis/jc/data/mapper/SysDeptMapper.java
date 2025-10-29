package cn.wxgis.jc.data.mapper;

import cn.wxgis.jc.common.web.dao.BaseDao;
import cn.wxgis.jc.system.po.SysDept;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysDeptMapper extends BaseDao<SysDept> {

    /**
     * 根据部门名称查询部门
     * @param name
     */
    SysDept findByName(@Param("name") String name);

}
