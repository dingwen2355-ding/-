package cn.wxgis.jc.system.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@ApiModel(value = "SysRoleSaveRequest", description = "角色保存 [传参]")
@Data
public class SysRoleSaveRequest {


    @ApiModelProperty(value = "角色id")
    private String id;

    @NotEmpty(message = "角色名称不能为空")
    @ApiModelProperty(value = "角色名称")
    private String name;

    @NotEmpty(message = "权限字符不能为空")
    @ApiModelProperty(value = "权限字符")
    private String code;

    @ApiModelProperty(value = "排序")
    private Integer orderNum;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "菜单列表")
    private List<String> menus;

    @ApiModelProperty(value = "数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）")
    private String dataScope;
}
