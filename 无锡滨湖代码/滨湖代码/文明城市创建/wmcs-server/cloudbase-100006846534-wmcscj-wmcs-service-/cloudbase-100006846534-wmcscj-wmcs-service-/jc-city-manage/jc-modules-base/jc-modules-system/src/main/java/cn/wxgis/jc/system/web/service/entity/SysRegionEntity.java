package cn.wxgis.jc.system.web.service.entity;

import cn.wxgis.jc.common.util.StringUtils;
import cn.wxgis.jc.security.utils.SecurityUtils;
import cn.wxgis.jc.system.po.SysRegion;
import cn.wxgis.jc.system.req.SysRegionSaveRequest;

import java.util.Date;

public class SysRegionEntity {

    public static SysRegion getSaveRegion(SysRegionSaveRequest request) {
        SysRegion data = new SysRegion();
        data.setId(request.getRegionCode());
        setCommonData(request, data);
        return data;
    }

    public static SysRegion getUpdateRegion(SysRegionSaveRequest request, SysRegion data) {
        setCommonData(request, data);
        return data;
    }

    private static void setCommonData(SysRegionSaveRequest request, SysRegion data) {
        data.setRegionName(request.getRegionName());
        data.setRegionCode(request.getRegionCode());
        data.setParentId(StringUtils.isNotBlank(request.getParentId()) ? request.getParentId() : "0");
        data.setOrderNum(request.getOrderNum());
        data.setAreaSituation(request.getAreaSituation());
        data.setRemark(request.getRemark());
    }
}
