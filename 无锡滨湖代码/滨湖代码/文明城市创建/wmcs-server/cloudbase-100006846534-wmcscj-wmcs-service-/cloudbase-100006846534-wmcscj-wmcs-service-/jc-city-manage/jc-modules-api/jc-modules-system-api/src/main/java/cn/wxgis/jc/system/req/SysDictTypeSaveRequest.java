package cn.wxgis.jc.system.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@ApiModel(value = "SysDictTypeSaveRequest", description = "字典类型保存 [传参]")
@Data
public class SysDictTypeSaveRequest {

    @ApiModelProperty(value = "字典类型id")
    private String id;

    @NotBlank(message = "请输入字典名称")
    @ApiModelProperty(value = "字典名称", required = true)
    private String dictName;

    @NotBlank(message = "请输入字典类型")
    @ApiModelProperty(value = "字典类型")
    private String dictType;

    @ApiModelProperty(value = "状态（0启用 1停用）")
    private Integer status;

    @ApiModelProperty(value = "备注")
    private String remark;
}
