package cn.wxgis.jc.system.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@ApiModel(value = "UpdatePasswordReqquest", description = "修改密码 [传参]")
@Data
public class UpdatePasswordRequest {

    @NotBlank(message = "原始密码不能为空")
    @ApiModelProperty(value = "原始密码", required = true)
    private String oldPassword;

    @NotBlank(message = "新密码不能为空")
    @ApiModelProperty(value = "新密码", required = true)
    private String newPassword;

}
