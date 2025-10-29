package cn.wxgis.jc.screen.domain.vo.count;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@ApiModel(value = "TodayVo", description = "待办、已办数信息")
@Accessors(chain = true)
@Data
public class TodayVo {

    @ApiModelProperty(value = "待办数")
    private Integer todoCount = 0;

    @ApiModelProperty(value = "办结数")
    private Integer finishCount = 0;

}
