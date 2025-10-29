package cn.wxgis.jc.data.service;

import cn.wxgis.jc.common.web.service.BaseService;
import cn.wxgis.jc.system.po.SysDept;

import java.util.List;

public interface SysDeptService extends BaseService<SysDept> {

    /**
     * 根据部门名称获取部门信息
     * @param name
     */
    SysDept findByName(String name);

    /**
     * 根据父id获取部门列表
     * @param parentId
     */
    List<SysDept> listByParentId(String parentId);

    /**
     * 根据政务部门id查询
     * @param wechatDeptId
     */
    boolean findByWechatDeptId(String wechatDeptId);
}
