package cn.wxgis.jc.screen.domain.vo.civilization;

import cn.wxgis.jc.civilization.data.po.DataCivilizationAdvanced;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel(value = "AdvancedVo", description = "先进典型表")
@Data
public class AdvancedVo extends DataCivilizationAdvanced {

    @ApiModelProperty(value = "先进典型类型")
    private String recommendName;

    @ApiModelProperty(value = "荣誉类型")
    private String type;

    @ApiModelProperty(value = "板块名称")
    private String regionName;

    @ApiModelProperty(value = "附件图列表")
    private List<String> attachmentUrl;

}
