package cn.wxgis.jc.system.resp;

import cn.wxgis.jc.system.po.SysDept;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@ApiModel(value = "SysDeptResponse", description = "部门 [返回信息]")
@Data
public class SysDeptResponse extends SysDept {

    @ApiModelProperty(value = "父级名称")
    private String parentName;

    @ApiModelProperty(value = "类型")
    private String typeName;


    @ApiModelProperty(value = "子类资源集合")
    private List<SysDeptResponse> children = new ArrayList<>();


    @ApiModelProperty(value = "督察员总数")
    private Integer volunteerCount;

    @ApiModelProperty(value = "政务微信组织名称")
    private String wechatDeptName;

    @ApiModelProperty(value = "街道全称")
    private String allname;

}
