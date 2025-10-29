package cn.wxgis.jc.system.web.service.entity;

import cn.wxgis.jc.common.constant.SecurityConstants;
import cn.wxgis.jc.common.enums.common.DelFlagEnum;
import cn.wxgis.jc.common.enums.system.UserStatusEnum;
import cn.wxgis.jc.security.utils.SecurityUtils;
import cn.wxgis.jc.system.po.SysUser;
import cn.wxgis.jc.system.req.SysUserSaveRequest;

import java.util.Date;

public class SysUserEntity {
    public static SysUser getSaveUser(SysUserSaveRequest request) {
        SysUser data = new SysUser();
        String newPassword = SecurityUtils.encryptPassword(SecurityConstants.DEFAULT_PASSWORD);
        data.setPassword(newPassword);
        setCommonData(request, data);
        return data;
    }

    public static SysUser getUpdateUser(SysUserSaveRequest request, SysUser data) {
        setCommonData(request, data);
        return data;
    }

    private static void setCommonData(SysUserSaveRequest request, SysUser data) {
        data.setUserName(request.getUserName());
        data.setNickName(request.getNickName());
        data.setPhonenumber(request.getPhonenumber());
        data.setEmail(request.getEmail());
        data.setSex(request.getSex());
        data.setDeptId(request.getDeptId());
        data.setPosition(request.getPosition());
        data.setStatus(request.getStatus());
        data.setAvatar(request.getAvatar());
    }


}
