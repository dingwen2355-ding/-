package cn.wxgis.jc.system.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@ApiModel(value = "SysMenuSaveRequest", description = "菜单保存 [传参]")
@Data
public class SysMenuSaveRequest {

    @ApiModelProperty(value = "菜单id")
    private String id;

    @NotEmpty(message = "菜单名称不能为空")
    @ApiModelProperty(value = "菜单名称")
    private String name;

    @ApiModelProperty(value = "父级id")
    private String parentId;

    @ApiModelProperty(value = "路由地址")
    private String path;

    @ApiModelProperty(value = "路由参数")
    private String query;

    @ApiModelProperty("组件链接")
    private String component;

    @ApiModelProperty(value = "权限标识")
    private String perms;

    @ApiModelProperty(value = "路径地址")
    private String link;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "菜单类型（M目录 C菜单 F按钮）")
    private String type;

    @ApiModelProperty(value = "显示顺序")
    private Integer orderNum;

    @ApiModelProperty(value = "是否外链")
    private Integer frameFlag;

    @ApiModelProperty(value = "菜单状态（0显示 1隐藏）")
    private Integer visible;

    @ApiModelProperty(value = "菜单状态（0正常 1停用）")
    private Integer status;

    @ApiModelProperty(value = "备用")
    private String remark;

    @ApiModelProperty(value = "所属平台：1pc；2app")
    private Integer platform;

}
