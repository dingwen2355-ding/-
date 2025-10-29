package com.grandlynn.spa.catalogue.dto;

import com.grandlynn.util.pojo.AbstractObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
@Data
@ApiModel(
   value = "MessageDTO对象",
   description = ""
)
public class MessageDTO extends AbstractObject implements Serializable {
   private static final long serialVersionUID = 1L;
   @ApiModelProperty("消息id")
   private Long messageId;
   @ApiModelProperty("人员id")
   private Long userId;
   @ApiModelProperty("消息标题")
   private String title;
   @ApiModelProperty("消息内容")
   private String content;
   @ApiModelProperty("消息类型")
   private Integer type;
   @ApiModelProperty("提交时间")
   private Long notificationTime;
   @ApiModelProperty("业务id")
   private Long businessId;
   @ApiModelProperty("阅读标识(已读：true，未读：false)")
   private Boolean read = false;
}
