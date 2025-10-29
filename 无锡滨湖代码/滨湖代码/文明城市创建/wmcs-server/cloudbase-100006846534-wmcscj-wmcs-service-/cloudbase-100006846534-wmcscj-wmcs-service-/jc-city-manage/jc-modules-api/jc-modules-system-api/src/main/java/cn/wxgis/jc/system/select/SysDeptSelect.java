package cn.wxgis.jc.system.select;

import cn.wxgis.jc.common.domain.param.PageParam;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@ApiModel(value = "SysDeptSelect", description = "部门查询 [传参]")
@Data
public class SysDeptSelect extends PageParam {

    @ApiModelProperty(value = "部门id")
    private String id;

    @ApiModelProperty(value = "类型：1-职能部门；2-区划部门")
    private Integer type;

    @ApiModelProperty(value = "部门名称")
    private String name;


}
