package cn.wxgis.jc.screen.domain.vo.civilization;

import cn.wxgis.jc.civilization.data.po.DataCivilizationCompany;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class CompanyVo extends DataCivilizationCompany {

    @ApiModelProperty(value = "宣传图")
    private String iconImg;

    @ApiModelProperty(value = "附件图列表")
    private List<String> attachmentUrl;

    @ApiModelProperty(value = "区划名称")
    private String regionName;

    @ApiModelProperty(value = "类型")
    private String type;

}
