package cn.wxgis.jc.system.web.service;

import cn.wxgis.jc.common.domain.exists.ExistsReturn;
import cn.wxgis.jc.common.web.service.BaseService;
import cn.wxgis.jc.system.po.SysDevice;
import cn.wxgis.jc.system.req.SysDeviceBindUserRequest;
import cn.wxgis.jc.system.req.SysDeviceSaveRequest;
import cn.wxgis.jc.system.resp.SysDeviceResponse;
import cn.wxgis.jc.system.select.SysDeviceSelect;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.Map;

public interface SysDeviceService extends BaseService<SysDevice> {

    /**
     * 新增
     */
    int save(SysDeviceSaveRequest request);

    /**
     * 编辑
     */
    int edit(SysDeviceSaveRequest request);

    /**
     * 删除
     */
    int delete(String id);

    /**
     * 绑定用户
     */
    int bind(SysDeviceBindUserRequest request);

    /**
     * 解绑用户
     */
    int unbind(SysDeviceBindUserRequest request);

    /**
     * 判断唯一性
     * @param existsParam
     */
    ExistsReturn exists(Map<String, String> existsParam);

    /**
     * 查询设备分页列表
     * @param select 查询条件
     */
    IPage<SysDeviceResponse> page(SysDeviceSelect select);

    /**
     * 根据id查询设备信息
     * @param id
     */
    SysDeviceResponse findResponseById(String id);

}
