package cn.wxgis.jc.screen.domain.vo.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ApiModel(value = "WfEventVo", description = "事件信息")
@Data
public class WfEventVo {

    @ApiModelProperty(value = "事件来源")
    private Integer eventSource;

    @ApiModelProperty(value = "事件id")
    private String id;

    @ApiModelProperty(value = "周期")
    private String cycle;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "检查时间")
    private Date checkDate;

    @ApiModelProperty(value = "是否延期")
    private Integer extensionFlag;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "计划时间")
    private Date planDate;

    @ApiModelProperty(value = "事件编号")
    private String code;

    @ApiModelProperty(value = "事件类型名称")
    private String eventTypeName;

    @ApiModelProperty(value = "存在问题")
    private String content;

    @ApiModelProperty(value = "检查项")
    private String checkItemName;

    @ApiModelProperty(value = "点位名称")
    private String pointName;

    @ApiModelProperty(value = "点位类型名称")
    private String typeName;

    @ApiModelProperty(value = "分数")
    private BigDecimal score;

    @ApiModelProperty(value = "区划名称")
    private String regionName;

    @ApiModelProperty(value = "纬度")
    private BigDecimal latitude;

    @ApiModelProperty(value = "经度")
    private BigDecimal longitude;

    @ApiModelProperty(value = "责任单位")
    private String deptNames;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "办结时间")
    private Date endTime;

    @ApiModelProperty(value = "事件状态")
    private String status;

    @ApiModelProperty(value = "设备id")
    private String channelId;

    @ApiModelProperty(value = "设备名称")
    private String deviceName;

    @ApiModelProperty(value = "图片")
    private List<String> images = new ArrayList<>();

}
