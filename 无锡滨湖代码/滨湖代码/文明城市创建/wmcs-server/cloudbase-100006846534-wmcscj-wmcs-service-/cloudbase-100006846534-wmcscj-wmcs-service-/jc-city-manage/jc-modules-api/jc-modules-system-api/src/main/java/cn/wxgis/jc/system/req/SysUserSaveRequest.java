package cn.wxgis.jc.system.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@ApiModel(value = "SysUserSaveRequest", description = "用户保存 [传参]")
@Data
public class SysUserSaveRequest {

    @ApiModelProperty(value = "用户id")
    private String id;

    @NotBlank(message = "用户账号不能为空")
    @ApiModelProperty(value = "用户账号")
    private String userName;

    @ApiModelProperty(value = "用户名称")
    private String nickName;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "手机号")
    private String phonenumber;

    @ApiModelProperty(value = "用户性别")
    private Integer sex;

    @ApiModelProperty(value = "用户状态")
    private Integer status;

    @ApiModelProperty(value = "用户头像")
    private String avatar;

    @ApiModelProperty(value = "归属部门id")
    private String deptId;

    @ApiModelProperty(value = "职位")
    private String position;

    @ApiModelProperty(value = "角色")
    private List<String> roles;
}
