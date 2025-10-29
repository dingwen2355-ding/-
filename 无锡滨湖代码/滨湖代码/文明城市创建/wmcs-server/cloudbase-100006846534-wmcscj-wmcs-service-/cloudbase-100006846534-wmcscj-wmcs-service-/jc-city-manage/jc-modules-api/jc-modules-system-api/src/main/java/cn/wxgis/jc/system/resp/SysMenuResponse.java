package cn.wxgis.jc.system.resp;

import cn.wxgis.jc.system.po.SysMenu;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel(value = "SysMenuResponse", description = "菜单 [返回信息]")
@Data
public class SysMenuResponse extends SysMenu {

    @ApiModelProperty(value = "父级名称")
    private String parentName;

    @ApiModelProperty(value = "子类资源集合")
    private List<SysMenuResponse> children;

}
