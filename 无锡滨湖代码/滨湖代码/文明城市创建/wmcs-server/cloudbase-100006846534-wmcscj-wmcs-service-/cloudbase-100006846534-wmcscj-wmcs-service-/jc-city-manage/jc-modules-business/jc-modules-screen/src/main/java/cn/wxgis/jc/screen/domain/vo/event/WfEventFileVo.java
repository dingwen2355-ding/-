package cn.wxgis.jc.screen.domain.vo.event;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "WfEventFileVo", description = "事件附件信息")
@Data
public class WfEventFileVo {

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "事件id")
    private String eventId;

    @ApiModelProperty(value = "图片名称")
    private String name;

    @ApiModelProperty(value = "oss名称")
    private String ossName;

    @ApiModelProperty(value = "大图地址")
    private String uriBig;

    @ApiModelProperty(value = "常规地址")
    private String url;

    @ApiModelProperty(value = "minio图片存放地址")
    private String file;

}
