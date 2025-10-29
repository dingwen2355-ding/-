package cn.wxgis.jc.system.web.service;

import cn.wxgis.jc.common.domain.exists.ExistsReturn;
import cn.wxgis.jc.common.web.service.BaseService;
import cn.wxgis.jc.system.po.SysDept;
import cn.wxgis.jc.system.req.SysDeptSaveRequest;
import cn.wxgis.jc.system.resp.SysDeptResponse;
import cn.wxgis.jc.system.select.SysDeptSelect;

import java.util.List;
import java.util.Map;

public interface SysDeptService extends BaseService<SysDept> {

    /**
     * 新增
     * @param request 部门信息
     * @return 新增条数
     */
    int save(SysDeptSaveRequest request);

    /**
     * 修改
     * @param request 部门信息
     * @return 修改条数
     */
    int edit(SysDeptSaveRequest request);

    /**
     * 删除
     * @param id 部门id
     * @return 删除条数
     */
    int delete(String id);

    /**
     * 查询部门唯一性
     * @param existsParam
     * @return 返回结果
     */
    ExistsReturn exists(Map<String, String> existsParam);

    /**
     * 查询部门树形结构
     * @return
     */
    List<SysDeptResponse> listDeptTree(SysDeptSelect select);

    /**
     * 查询数据
     * @param id 部门id
     * @return 部门信息
     */
    SysDeptResponse findResponseById(String id);

    /**
     * 根据父级id查询部门信息
     */
    SysDeptResponse getAllNameByDept(String id);

    /**
     * 查询部门是否存在下级部门
     * @param id
     * @return
     */
    boolean hasChildById(String id);

    /**
     * 查询部门下是否存在用户
     * @param id
     * @return
     */
    boolean checkDeptExistUser(String id);

}
