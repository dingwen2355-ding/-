package cn.wxgis.jc.workflow.resp;

import cn.wxgis.jc.workflow.po.WfNodeHistory;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ApiModel(value = "WfNodeHistoryResponse", description = "流程历史节点信息 [返回信息]")
@Data
public class WfNodeHistoryResponse extends WfNodeHistory {

    //图片资源
    @ApiModelProperty(value = "图片资源")
    List<Map<String,String>> pictureList = new ArrayList<>();

    //文件资源
    @ApiModelProperty(value = "文件资源")
    List<Map<String,String>> docFileList = new ArrayList<>();

    //文件资源
    @ApiModelProperty(value = "附件列表")
    List<String> attchpathList = new ArrayList<>();

}
