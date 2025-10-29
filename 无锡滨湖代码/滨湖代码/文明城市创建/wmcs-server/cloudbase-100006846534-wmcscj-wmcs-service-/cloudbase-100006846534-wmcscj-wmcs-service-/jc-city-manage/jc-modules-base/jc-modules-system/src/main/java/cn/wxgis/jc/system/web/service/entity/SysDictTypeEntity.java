package cn.wxgis.jc.system.web.service.entity;

import cn.wxgis.jc.common.util.id.IdUtils;
import cn.wxgis.jc.security.utils.SecurityUtils;
import cn.wxgis.jc.system.po.SysDictType;
import cn.wxgis.jc.system.req.SysDictTypeSaveRequest;

import java.util.Date;

public class SysDictTypeEntity {

    public static SysDictType getSaveDictType(SysDictTypeSaveRequest request) {
        SysDictType data = new SysDictType();
        setCommonData(request, data);
        return data;
    }

    public static SysDictType getUpdateDictType(SysDictTypeSaveRequest request, SysDictType data) {
        setCommonData(request, data);
        return data;
    }

    private static void setCommonData(SysDictTypeSaveRequest request, SysDictType data) {
        data.setDictName(request.getDictName());
        data.setDictType(request.getDictType());
        data.setStatus(request.getStatus());
        data.setRemark(request.getRemark());
    }
}
