package cn.wxgis.jc.screen.domain.vo.civilization;

import cn.wxgis.jc.civilization.data.po.DataVolunteerActivity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel(value = "ActivityVo", description = "活动列表")
@Data
public class ActivityVo extends DataVolunteerActivity {

    @ApiModelProperty(value = "宣传图")
    private String iconImg;

    @ApiModelProperty(value = "附件图列表")
    private List<String> attachmentUrl;

    @ApiModelProperty(value = "区划名称")
    private String regionName;

    @ApiModelProperty(value = "类型")
    private String typeName;

}
