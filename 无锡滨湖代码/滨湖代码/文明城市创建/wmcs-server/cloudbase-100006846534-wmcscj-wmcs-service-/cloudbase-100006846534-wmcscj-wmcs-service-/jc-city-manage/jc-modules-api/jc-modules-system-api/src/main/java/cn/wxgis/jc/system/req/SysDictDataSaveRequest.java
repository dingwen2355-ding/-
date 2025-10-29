package cn.wxgis.jc.system.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@ApiModel(value = "SysDictDataSaveRequest", description = "字典数据保存 [传参]")
@Data
public class SysDictDataSaveRequest {

    @ApiModelProperty(value = "字典数据id")
    private String id;

    @NotBlank(message = "字典类型不能为空")
    @ApiModelProperty(value = "字典类型")
    private String dictType;

    @NotBlank(message = "字典标签不能为空")
    @ApiModelProperty(value = "字典标签")
    private String dictLabel;

    @NotBlank(message = "字典值不能为空")
    @ApiModelProperty(value = "字典值")
    private String dictValue;

    @ApiModelProperty(value = "排序")
    private Integer orderNum;

    @ApiModelProperty(value = "状态（0启用 1停用）")
    private Integer status;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value ="操作：1-新增；2-修改；3-删除")
    private Integer operate;
}
