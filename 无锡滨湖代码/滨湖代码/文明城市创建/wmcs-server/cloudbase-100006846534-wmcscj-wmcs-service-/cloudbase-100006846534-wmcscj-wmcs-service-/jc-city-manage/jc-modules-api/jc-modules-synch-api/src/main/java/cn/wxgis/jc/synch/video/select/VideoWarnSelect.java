package cn.wxgis.jc.synch.video.select;

import cn.wxgis.jc.common.domain.param.PageParam;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel(value = "VideoWarnSelect", description = "视频通道查询 [传参]")
@Data
public class VideoWarnSelect extends PageParam {

}
