package com.znv.manage.common.bean.event;

import java.util.Date;
import java.io.Serializable;

import lombok.Data;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

/**
 * 事件核查反馈表(TCfgEventCheckFeedback)实体类
 *
 * @author makejava
 * @since 2023-05-18 15:18:58
 */
@Data
public class EventCheckFeedback implements Serializable {
    private static final long serialVersionUID = 149926080990478048L;
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Integer id;
    /**
     * 是否属实
     */
    @ApiModelProperty(value = "是否属实")
    private String isTrue;
    /**
     * 核查时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "核查时间")
    private Date checkTime;
    /**
     * 核查内容
     */
    @ApiModelProperty(value = "核查内容")
    private String checkContent;
    /**
     * 附件地址
     */
    @ApiModelProperty(value = "附件地址")
    private String fileUrls;
    /**
     * 核查表id
     */
    @ApiModelProperty(value = "核查表id")
    private Integer checkId;

}
