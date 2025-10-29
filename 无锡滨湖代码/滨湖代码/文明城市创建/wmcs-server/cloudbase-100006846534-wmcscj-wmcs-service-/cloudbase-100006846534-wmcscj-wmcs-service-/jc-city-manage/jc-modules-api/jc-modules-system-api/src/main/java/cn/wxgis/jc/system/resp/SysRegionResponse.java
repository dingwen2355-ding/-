
package cn.wxgis.jc.system.resp;

import cn.wxgis.jc.system.po.SysRegion;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@ApiModel(value = "SysRegionResponse", description = "区划 [返回信息]")
@Data
public class SysRegionResponse extends SysRegion {

    @ApiModelProperty(value = "父级名称")
    private String parentName;

    @ApiModelProperty(value = "子类资源集合")
    private List<SysRegionResponse> children = new ArrayList<>();

}
