package cn.wxgis.jc.synch.unify.select;

import cn.wxgis.jc.common.domain.param.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "UnifyUserSelect", description = "第三方部门查询 [传参]")
@Data
public class UnifyUserSelect extends PageParam {

    @ApiModelProperty(value = "账号")
    private String account;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "手机号码")
    private String majorMobile;

    @ApiModelProperty(value = "所在部门")
    private String majorDept;

}
