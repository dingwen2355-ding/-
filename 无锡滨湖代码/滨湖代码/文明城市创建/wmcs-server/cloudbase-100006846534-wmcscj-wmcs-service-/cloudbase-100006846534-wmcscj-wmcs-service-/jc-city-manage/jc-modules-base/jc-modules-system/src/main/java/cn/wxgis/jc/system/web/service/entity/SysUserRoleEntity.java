package cn.wxgis.jc.system.web.service.entity;

import cn.wxgis.jc.common.util.StringUtils;
import cn.wxgis.jc.system.po.SysUserRole;

import java.util.ArrayList;
import java.util.List;

public class SysUserRoleEntity {

    public static List<SysUserRole> getSaveUserRoleList(String userId, List<String> roles) {
        List<SysUserRole> resultList = new ArrayList<>();
        if (StringUtils.isEmpty(roles) || roles.size() <= 0) return resultList;
        roles.forEach(rid -> {
            SysUserRole userRole = new SysUserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(rid);
            resultList.add(userRole);
        });
        return resultList;
    }

}
