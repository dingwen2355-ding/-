package cn.wxgis.jc.system.web.service;

import cn.wxgis.jc.common.domain.exists.ExistsReturn;
import cn.wxgis.jc.common.web.service.BaseService;
import cn.wxgis.jc.system.po.SysDictType;
import cn.wxgis.jc.system.req.SysDictTypeSaveRequest;
import cn.wxgis.jc.system.select.SysDictTypeSelect;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.Map;

public interface SysDictTypeService extends BaseService<SysDictType> {

    /**
     * 新增
     * @param request 字典类型信息
     * @return 新增条数
     */
    int save(SysDictTypeSaveRequest request);

    /**
     * 修改
     * @param request 字典类型信息
     * @return 修改条数
     */
    int edit(SysDictTypeSaveRequest request);

    /**
     * 删除
     * @param id 字典类型id
     * @return 删除条数
     */
    int delete(String id);

    /**
     * 验证唯一性
     * @param existsParam 验证参数
     * @return 返回结果
     */
    ExistsReturn exists(Map<String, String> existsParam);

    /**
     * 分页查询
     * @param select 查询参数
     * @return 字典类型列表
     */
    IPage<SysDictType> page(SysDictTypeSelect select);

    /**
     * 加载字典缓存数据
     */
    void loadingDictCache();

    /**
     * 清空字典缓存数据
     */
    void clearDictCache();

    /**
     * 重置字典缓存数据
     */
    void resetDictCache();

}
