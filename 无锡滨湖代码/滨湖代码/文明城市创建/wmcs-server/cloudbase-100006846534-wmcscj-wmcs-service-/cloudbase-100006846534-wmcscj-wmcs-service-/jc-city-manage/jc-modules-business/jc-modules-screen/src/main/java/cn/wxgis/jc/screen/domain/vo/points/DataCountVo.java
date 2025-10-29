package cn.wxgis.jc.screen.domain.vo.points;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "DataCount", description = "首页数据统计")
@Data
public class DataCountVo {

    @ApiModelProperty(value = "工单总数")
    private Integer eventCount;

    @ApiModelProperty(value = "点位类型总数")
    private Integer pointsTypeCount;

    @ApiModelProperty(value = "点位总数")
    private Integer pointsCount;

    @ApiModelProperty(value = "问题点位数")
    private Integer eventPointsCount;

}
