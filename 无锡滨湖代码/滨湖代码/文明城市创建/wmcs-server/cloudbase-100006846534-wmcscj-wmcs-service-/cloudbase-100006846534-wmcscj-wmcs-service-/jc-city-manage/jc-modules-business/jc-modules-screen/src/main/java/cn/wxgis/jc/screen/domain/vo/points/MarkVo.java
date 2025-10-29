package cn.wxgis.jc.screen.domain.vo.points;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MarkVo {

    @ApiModelProperty(value = "巡查员id")
    private String userId;

    @ApiModelProperty(value = "检查点位id")
    private String checkPointsId;
}
