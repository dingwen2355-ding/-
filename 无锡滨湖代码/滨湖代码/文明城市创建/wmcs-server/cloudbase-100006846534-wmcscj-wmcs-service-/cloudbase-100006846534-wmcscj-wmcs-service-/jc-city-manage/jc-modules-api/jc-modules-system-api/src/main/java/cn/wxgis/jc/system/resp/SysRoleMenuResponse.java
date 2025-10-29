package cn.wxgis.jc.system.resp;

import cn.wxgis.jc.system.po.SysRoleMenu;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "RoleMenuResponse", description = "角色菜单 [返回信息]")
@Data
public class SysRoleMenuResponse extends SysRoleMenu {

    @ApiModelProperty(value = "上级资源id")
    private String menuParentId;

}
