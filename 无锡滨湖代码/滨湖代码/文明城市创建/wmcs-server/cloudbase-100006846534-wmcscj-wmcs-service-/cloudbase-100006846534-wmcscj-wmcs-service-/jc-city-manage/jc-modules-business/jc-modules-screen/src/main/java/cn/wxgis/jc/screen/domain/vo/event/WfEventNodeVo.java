package cn.wxgis.jc.screen.domain.vo.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel(value = "WfEventNodeVo", description = "事件节点信息")
@Data
public class WfEventNodeVo {

    @ApiModelProperty(value = "事件来源")
    private Integer eventSource;

    @ApiModelProperty(value = "流程节点id")
    private String id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "处理时间")
    private Date handletime;

    @ApiModelProperty(value = "处理意见")
    private String handleopinion;

    @ApiModelProperty(value = "节点id")
    private String nodeid;

    @ApiModelProperty(value = "节点名称")
    private String nodename;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "处置时限")
    private Date timelimit;

    @ApiModelProperty(value = "处理单位")
    private String handlerDept;

    @ApiModelProperty(value = "处理人id")
    private String handlerid;

    @ApiModelProperty(value = "处理人账号")
    private String userName;

    @ApiModelProperty(value = "处理人姓名")
    private String nickName;

    @ApiModelProperty(value = "附件")
    private String attchpath;
}
