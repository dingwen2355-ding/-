package cn.wxgis.jc.screen.domain.vo.points;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CheckUserVo {

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "联系方式")
    private String phone;
}
