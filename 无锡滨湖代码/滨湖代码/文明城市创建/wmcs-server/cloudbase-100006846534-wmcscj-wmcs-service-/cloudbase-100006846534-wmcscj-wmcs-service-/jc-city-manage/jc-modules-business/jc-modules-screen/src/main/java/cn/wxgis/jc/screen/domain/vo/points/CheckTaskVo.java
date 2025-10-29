package cn.wxgis.jc.screen.domain.vo.points;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CheckTaskVo {

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "类型：1-常规类型；2-特殊类型")
    private String type;


    @ApiModelProperty(value = "点位类型名称")
    private String pointsTypeName;

    @ApiModelProperty(value = "点位名称")
    private String pointsName;

    @ApiModelProperty(value = "检查项")
    private String checkItem;

    @ApiModelProperty(value = "巡查内容")
    private String content;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private String createTime;

    @ApiModelProperty(value = "状态：0-待巡查、1-合格、2-不合格已处理、3-不合格处理中、4-不合格待处理")
    private String status;
}
