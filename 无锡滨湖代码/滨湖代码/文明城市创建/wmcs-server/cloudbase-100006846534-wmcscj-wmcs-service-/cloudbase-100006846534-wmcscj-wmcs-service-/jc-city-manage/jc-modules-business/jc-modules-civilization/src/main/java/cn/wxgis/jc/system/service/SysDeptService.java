package cn.wxgis.jc.system.service;

import cn.wxgis.jc.common.web.service.BaseService;
import cn.wxgis.jc.system.po.SysDept;
import cn.wxgis.jc.system.resp.SysDeptResponse;

import java.util.List;

public interface SysDeptService extends BaseService<SysDept> {

    /**
     * 根据id集合查询部门列表
     * @param ids
     */
    List<SysDept> listByIds(List<String> ids);

    /**
     * 根据父id
     * @param parentId
     * @return
     */
    List<SysDept> listByParentId(String parentId);

    /**
     * 根据类型查询部门列表
     * @param type
     */
    List<SysDept> listByType(Integer type);

    /**
     * 根据父级id查询部门信息
     */
    SysDeptResponse getAllNameByDept(String id);

}
