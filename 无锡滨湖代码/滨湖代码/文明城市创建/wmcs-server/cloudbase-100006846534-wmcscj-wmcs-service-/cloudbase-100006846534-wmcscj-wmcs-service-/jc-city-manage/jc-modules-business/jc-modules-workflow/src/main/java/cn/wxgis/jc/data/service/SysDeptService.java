package cn.wxgis.jc.data.service;

import cn.wxgis.jc.common.web.service.BaseService;
import cn.wxgis.jc.system.po.SysDept;
import cn.wxgis.jc.system.po.SysUser;

import java.util.List;

public interface SysDeptService extends BaseService<SysDept> {

    List<SysDept> listByIds(List<String> ids);

    SysDept findByUserId(String userId);

    List<SysDept> listByParentId(String deptId);

    List<SysDept> getDeptIdsByUser(String handlerid);

    /**
     * 根据名称查询部门信息
     * @param regionName 区划名称
     */
    SysDept findByName(String regionName);

}
