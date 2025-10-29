package cn.wxgis.jc.system.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@ApiModel(value = "SysRegionSaveRequest", description = "区划保存 [传参]")
@Data
public class SysRegionSaveRequest {

    @ApiModelProperty(value = "区划id")
    private String id;

    @NotBlank(message = "区划编码不能为空")
    @ApiModelProperty(value = "区划编码")
    private String regionCode;

    @NotBlank(message = "区划名称不能为空")
    @ApiModelProperty(value = "区划名称")
    private String regionName;

    @ApiModelProperty(value = "区域概况")
    private String areaSituation;

    @ApiModelProperty(value = "父级id")
    private String parentId;

    @ApiModelProperty(value = "区域类型 0-基础网格，1-9-专属网格 1-行政中心；2-各类园区；3-商务楼宇；4-商圈市场；5-学校；6-农林场；7-医疗卫生机构；8-企事业单位；9-其他")
    private Integer regionType;

    @ApiModelProperty(value = "地图初始化级别")
    private Integer mapLevel;

    @ApiModelProperty(value = "区域级别")
    private Integer level;

    @ApiModelProperty(value = "排序")
    private Integer orderNum;

    @ApiModelProperty(value = "是否县级市")
    private Integer countyFlag;

    @ApiModelProperty(value = "备注")
    private String remark;
}
