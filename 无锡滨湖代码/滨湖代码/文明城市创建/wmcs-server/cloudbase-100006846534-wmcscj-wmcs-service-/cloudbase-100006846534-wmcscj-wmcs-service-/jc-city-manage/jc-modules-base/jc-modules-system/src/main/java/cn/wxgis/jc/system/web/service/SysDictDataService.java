package cn.wxgis.jc.system.web.service;

import cn.wxgis.jc.common.domain.exists.ExistsReturn;
import cn.wxgis.jc.common.web.service.BaseService;
import cn.wxgis.jc.system.model.DictVO;
import cn.wxgis.jc.system.model.SysDictDataVO;
import cn.wxgis.jc.system.po.SysDictData;
import cn.wxgis.jc.system.req.SysDictDataSaveRequest;
import cn.wxgis.jc.system.select.SysDictDataSelect;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;
import java.util.Map;

public interface SysDictDataService extends BaseService<SysDictData> {

    /**
     * 新增
     * @param request 字典数据
     * @return 新增条数
     */
    int save(SysDictDataSaveRequest request);

    /**
     * 修改
     * @param request 字典数据
     * @return 修改条数
     */
    int edit(SysDictDataSaveRequest request);

    /**
     * 删除
     * @param id 字典数据id
     * @return 删除条数
     */
    int delete(String id);

    /**
     * 设置数据类型的值
     * @param requests 数据值
     * @return 设置条数
     */
    int set(List<SysDictDataSaveRequest> requests);

    /**
     * 验证唯一性
     * @param existsParam 验证参数
     * @return 结果
     */
    ExistsReturn exists( Map<String, String> existsParam);

    /**
     * 分页查询
     * @param select 查询条件
     * @return 字典数据分页
     */
    IPage<SysDictData> page(SysDictDataSelect select);

    /**
     * 根据查询参数查询列表
     * @param dictType 字典类型
     * @return 字典数据列表
     */
    List<SysDictData> listByDictType(String dictType);

    /**
     * 根据dictType查询可选字典数据
     * @param dictType 字典类型
     * @return 字典数据列表
     */
    List<SysDictData> getDictDataByDictType(String dictType);

    /**
     * 查询所有字典
     */
    List<SysDictDataVO> getAllDict();

    /**
     * 查询封装的所有字典
     * @return
     */
    List<DictVO> dict();

}
