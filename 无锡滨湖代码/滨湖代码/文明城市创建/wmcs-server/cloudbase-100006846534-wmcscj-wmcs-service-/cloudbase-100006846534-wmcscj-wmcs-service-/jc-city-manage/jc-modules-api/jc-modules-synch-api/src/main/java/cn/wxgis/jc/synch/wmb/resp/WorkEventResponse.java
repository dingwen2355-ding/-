package cn.wxgis.jc.synch.wmb.resp;

import cn.wxgis.jc.synch.wmb.po.WorkEvent;
import cn.wxgis.jc.synch.wmb.po.WorkEventFile;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel(value = "WorkEventResponse", description = "阈值管理 [返回信息]")
@Data
public class WorkEventResponse extends WorkEvent {

    @ApiModelProperty(value = "附件列表")
    private List<WorkEventFile> fileList;
}
