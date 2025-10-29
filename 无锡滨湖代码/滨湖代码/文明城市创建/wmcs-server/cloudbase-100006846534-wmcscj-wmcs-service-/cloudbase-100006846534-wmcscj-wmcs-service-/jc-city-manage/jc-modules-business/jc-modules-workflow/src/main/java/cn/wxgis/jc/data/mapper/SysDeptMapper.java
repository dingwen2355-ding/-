package cn.wxgis.jc.data.mapper;

import cn.wxgis.jc.common.web.dao.BaseDao;
import cn.wxgis.jc.system.po.SysDept;
import cn.wxgis.jc.system.po.SysRegion;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysDeptMapper extends BaseDao<SysDept> {

    /**
     * 根据部门id查询政务微信部门id
     * @param id
     */
    String findWechatDeptIdById(@Param("id") String id);

    /**
     * 根据部门ID查询区划信息
     * @param deptId 部门ID
     */
    SysRegion findRegionByDeptId(@Param("deptId") String deptId);
}
