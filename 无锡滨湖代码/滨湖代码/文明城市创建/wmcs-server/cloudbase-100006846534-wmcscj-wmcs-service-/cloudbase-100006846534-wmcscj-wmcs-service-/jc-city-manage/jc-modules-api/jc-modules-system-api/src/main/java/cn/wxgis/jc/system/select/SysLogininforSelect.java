package cn.wxgis.jc.system.select;

import cn.wxgis.jc.common.domain.param.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "SysLogininforSelect", description = "系统日志查询 [传参]")
@Data
public class SysLogininforSelect extends PageParam {

    @ApiModelProperty(value = "登陆账号")
    private String userName;

    @ApiModelProperty(value = "模块标题")
    private String status;

    @ApiModelProperty(value = "查询开始时间")
    private String beginTime;

    @ApiModelProperty(value = "查询结束时间")
    private String endTime;
}
