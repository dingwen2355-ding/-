package cn.wxgis.jc.system.web.service.entity;

import cn.wxgis.jc.common.util.id.IdUtils;
import cn.wxgis.jc.security.utils.SecurityUtils;
import cn.wxgis.jc.system.po.SysRole;
import cn.wxgis.jc.system.req.SysRoleSaveRequest;

import java.util.Date;

public class SysRoleEntity {
    public static SysRole getSaveRole(SysRoleSaveRequest result) {
        SysRole data = new SysRole();
        setCommonData(result, data);
        return data;
    }

    public static SysRole getUpdateRole(SysRoleSaveRequest request, SysRole data) {
        setCommonData(request, data);
        return data;
    }

    private static void setCommonData(SysRoleSaveRequest request, SysRole data) {
        data.setName(request.getName());
        data.setCode(request.getCode());
        data.setOrderNum(request.getOrderNum());
        data.setDataScope(request.getDataScope());
    }

}
