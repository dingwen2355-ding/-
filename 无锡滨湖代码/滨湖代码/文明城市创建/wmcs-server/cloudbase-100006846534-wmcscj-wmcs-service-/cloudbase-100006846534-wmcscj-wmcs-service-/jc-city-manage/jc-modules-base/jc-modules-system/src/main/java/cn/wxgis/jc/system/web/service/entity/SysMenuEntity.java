package cn.wxgis.jc.system.web.service.entity;

import cn.wxgis.jc.common.util.id.IdUtils;
import cn.wxgis.jc.security.utils.SecurityUtils;
import cn.wxgis.jc.system.po.SysMenu;
import cn.wxgis.jc.system.req.SysMenuSaveRequest;

import java.util.Date;

public class SysMenuEntity {

    public static SysMenu getSaveMenu(SysMenuSaveRequest request) {
        SysMenu data = new SysMenu();
        setCommonData(request, data);
        return data;
    }

    public static SysMenu getUpdateMenu(SysMenuSaveRequest request, SysMenu data) {
        setCommonData(request, data);
        return data;
    }

    private static void setCommonData(SysMenuSaveRequest request, SysMenu data) {
        data.setName(request.getName());
        data.setPath(request.getPath());
        data.setQuery(request.getQuery());
        data.setComponent(request.getComponent());
        data.setPerms(request.getPerms());
        data.setIcon(request.getIcon());
        data.setLink(request.getLink());
        data.setType(request.getType());
        data.setOrderNum(request.getOrderNum());
        data.setFrameFlag(request.getFrameFlag());
        data.setVisible(request.getVisible());
        data.setStatus(request.getStatus());
        data.setRemark(request.getRemark());
        data.setParentId(request.getParentId());
        data.setPlatform(request.getPlatform());
    }
}
