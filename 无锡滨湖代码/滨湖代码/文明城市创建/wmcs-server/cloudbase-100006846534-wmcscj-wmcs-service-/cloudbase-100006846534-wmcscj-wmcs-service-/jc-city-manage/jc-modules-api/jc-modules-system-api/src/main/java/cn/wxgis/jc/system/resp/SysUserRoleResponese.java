package cn.wxgis.jc.system.resp;

import cn.wxgis.jc.system.po.SysRole;
import cn.wxgis.jc.system.po.SysUser;
import cn.wxgis.jc.system.po.SysUserRole;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@ApiModel(value = "SysUserRoleResponese", description = "用户角色 [返回信息]")
@Data
public class SysUserRoleResponese extends SysUserRole {

    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty("角色id")
    private String roleId;

    @ApiModelProperty(value = "角色列表")
    private List<SysRole> roles = new ArrayList<>();

    @ApiModelProperty(value = "用户列表")
    private List<SysUser> users = new ArrayList<>();

}
