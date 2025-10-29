package cn.wxgis.jc.system.model;

import cn.wxgis.jc.system.po.SysRole;
import lombok.Data;

@Data
public class SysUserRoleVO {

    private String userId;

    private String roleId;

    private SysRole role;
}
