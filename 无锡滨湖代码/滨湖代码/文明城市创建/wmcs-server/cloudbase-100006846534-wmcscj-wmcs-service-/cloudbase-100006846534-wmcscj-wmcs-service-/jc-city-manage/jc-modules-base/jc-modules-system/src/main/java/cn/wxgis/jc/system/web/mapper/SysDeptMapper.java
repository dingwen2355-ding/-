package cn.wxgis.jc.system.web.mapper;

import cn.wxgis.jc.common.web.dao.BaseDao;
import cn.wxgis.jc.system.po.SysDept;
import cn.wxgis.jc.system.resp.SysDeptResponse;
import cn.wxgis.jc.system.select.SysDeptSelect;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface SysDeptMapper extends BaseDao<SysDept> {

    /**
     * 查询部门列表
     */
    List<SysDeptResponse> voList(SysDeptSelect select);

    /**
     * 根据id获取部门信息
     */
    SysDeptResponse findResponseById(@Param("id") String id);
}
