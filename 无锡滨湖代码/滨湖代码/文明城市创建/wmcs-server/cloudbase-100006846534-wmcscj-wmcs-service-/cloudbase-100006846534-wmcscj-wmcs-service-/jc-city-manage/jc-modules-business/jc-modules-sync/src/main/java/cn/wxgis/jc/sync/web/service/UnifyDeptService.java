package cn.wxgis.jc.sync.web.service;

import cn.wxgis.jc.common.web.service.BaseService;
import cn.wxgis.jc.synch.unify.po.UnifyDept;

import java.util.List;

public interface UnifyDeptService extends BaseService<UnifyDept> {

    /**
     * 根据父id获取政务微信部门列表
     * @param parentId
     */
    List<UnifyDept> listByParentId(String parentId);
}
