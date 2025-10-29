package cn.wxgis.jc.synch.unify.resp;

import cn.wxgis.jc.synch.unify.po.UnifyDept;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel(value = "UnifyDeptResponse", description = "第三方部门 [返回信息]")
@Data
public class UnifyDeptResponse extends UnifyDept {

    @ApiModelProperty(value = "父级名称")
    private String parentName;

    @ApiModelProperty(value = "子类资源集合")
    private List<UnifyDeptResponse> children;

}
