package cn.wxgis.jc.system.web.service;

import cn.wxgis.jc.common.domain.exists.ExistsReturn;
import cn.wxgis.jc.common.web.service.BaseService;
import cn.wxgis.jc.system.po.SysRegion;
import cn.wxgis.jc.system.req.SysRegionSaveRequest;
import cn.wxgis.jc.system.resp.SysRegionResponse;
import cn.wxgis.jc.system.select.SysRegionSelect;

import java.util.List;
import java.util.Map;

public interface SysRegionService extends BaseService<SysRegion> {

    /**
     * 新增数据
     * @param request 区划信息
     * @return 新增条数
     */
    int save(SysRegionSaveRequest request);

    /**
     * 修改数据
     * @param request 区划信息
     * @return 修改条数
     */
    int edit(SysRegionSaveRequest request);

    /**
     * 删除数据
     * @param id 区划id
     * @return 删除条数
     */
    int delete(String id);

    /**
     * 更新街道所归属的部门
     * @return
     */
    int updateByDept();

    /**
     * 验证唯一性
     * @param existsParam 验证参数
     * @return 结果
     */
    ExistsReturn exists(Map<String, String> existsParam);

    /**
     * 查询列表
     * @param select 条件查询
     * @return 区划列表
     */
    List<SysRegionResponse> listRegionTree(SysRegionSelect select);

    /**
     * 根据区划编码查询数据
     * @param regionCode 区划编码
     * @return 区划信息
     */
    SysRegion findByRegionCode(String regionCode);

    /**
     * 根据区划名称查询数据
     * @param regionName 区划名称
     * @return 区划信息
     */
    SysRegion findByRegionName(String regionName);

    /**
     * 根据id查询数据
     * @param id 区划id
     * @return 区划信息
     */
    SysRegionResponse findResponseById(String id);

}
