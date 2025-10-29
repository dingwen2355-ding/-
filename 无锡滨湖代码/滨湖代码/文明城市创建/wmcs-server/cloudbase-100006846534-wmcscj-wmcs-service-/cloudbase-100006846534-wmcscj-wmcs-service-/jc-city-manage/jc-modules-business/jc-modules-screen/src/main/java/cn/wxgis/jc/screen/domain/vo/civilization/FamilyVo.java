package cn.wxgis.jc.screen.domain.vo.civilization;

import cn.wxgis.jc.civilization.data.po.DataCivilizationFamily;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class FamilyVo extends DataCivilizationFamily {

    @ApiModelProperty(value = "宣传图")
    private String iconImg;

    @ApiModelProperty(value = "附件图列表")
    private List<String> attachmentUrl;

    @ApiModelProperty(value = "区划名称")
    private String regionName;

}
