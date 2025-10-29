package cn.wxgis.jc.system.web.service.entity;

import cn.wxgis.jc.common.util.id.IdUtils;
import cn.wxgis.jc.system.po.SysDevice;
import cn.wxgis.jc.system.req.SysDeviceSaveRequest;

public class SysDeviceEntity {
    public static SysDevice getSaveDevice(SysDeviceSaveRequest request) {
        SysDevice data = new SysDevice();
        setCommonData(data, request);
        return data;
    }

    public static SysDevice getUpdateDevice(SysDevice data, SysDeviceSaveRequest request) {
        setCommonData(data, request);
        return data;
    }

    private static void setCommonData(SysDevice data, SysDeviceSaveRequest request) {
        data.setName(request.getName());
        data.setCode(request.getCode());
    }


}
