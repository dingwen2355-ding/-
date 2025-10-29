package cn.wxgis.jc.workflow.web.select;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EventCountSelect {

    @ApiModelProperty(value = "第几期")
    private String cycle;

}
