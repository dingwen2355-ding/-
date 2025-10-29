package cn.wxgis.jc.synch.wmb.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WmbCycleVo {

    @ApiModelProperty(value = "开始日期")
    private String defaultDate;

    @ApiModelProperty(value = "周期名称")
    private String name;

    @ApiModelProperty(value = "名称值")
    private String nameValue;

    @ApiModelProperty(value = "截至时间")
    private String posttime;

}
