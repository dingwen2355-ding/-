package cn.wxgis.jc.synch.unify.resp;

import cn.wxgis.jc.synch.unify.po.UnifyUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "UnifyUserResponse", description = "第三方用户 [返回信息]")
@Data
public class UnifyUserResponse extends UnifyUser {

    @ApiModelProperty(value = "部门名称")
    private String majorDeptName;

}
