package cn.wxgis.jc.system.select;

import cn.wxgis.jc.common.domain.param.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "SysOperLogSelect", description = "操作日志查询 [传参]")
@Data
public class SysOperLogSelect extends PageParam {

    @ApiModelProperty(value = "模块标题")
    private String title;

    @ApiModelProperty(value = "操作人员")
    private String operName;

    @ApiModelProperty(value = "请求方法")
    private String requestMethod;

    @ApiModelProperty(value = "方法名称")
    private String method;

    @ApiModelProperty(value = "查询开始时间")
    private String beginTime;

    @ApiModelProperty(value = "查询结束时间")
    private String endTime;
}
