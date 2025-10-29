package cn.wxgis.jc.system.resp;

import cn.wxgis.jc.system.po.SysUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "SysUserResponse", description = "用户 [返回信息]")
@Data
public class SysUserResponse extends SysUser {

    @ApiModelProperty(value = "单位名称")
    private String deptName;

    @ApiModelProperty(value = "所属角色")
    private String[] roles;

    @ApiModelProperty(value = "所属角色")
    private String roleNames;

}
