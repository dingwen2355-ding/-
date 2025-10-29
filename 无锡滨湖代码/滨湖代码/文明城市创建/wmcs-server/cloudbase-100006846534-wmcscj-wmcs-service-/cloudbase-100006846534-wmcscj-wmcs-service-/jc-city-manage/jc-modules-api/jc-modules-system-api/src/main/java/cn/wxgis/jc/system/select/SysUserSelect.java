package cn.wxgis.jc.system.select;

import cn.wxgis.jc.common.domain.param.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "SysUserSelect", description = "用户查询 [传参]")
@Data
public class SysUserSelect extends PageParam {

    @ApiModelProperty(value = "登陆账号")
    private String userName;

    @ApiModelProperty(value = "用户姓名")
    private String nickName;

    @ApiModelProperty(value = "所属部门")
    private String deptId;

    @ApiModelProperty(value = "手机号")
    private String phonenumber;

    @ApiModelProperty(value = "职位")
    private String position;

    @ApiModelProperty(value = "用户状态")
    private Integer status;
}
