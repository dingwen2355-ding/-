package com.znv.manage.common.bean.tip;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class MessagePrecinctBean {
    private String id;
    private String pMessageId;
    private String messageType; //消息状态: 1 未读  2 已读

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date receiveTime; //接收时间

    private String receivePhone; //接收人联系方式
    private String receivePerson; //接收人
    private String receivePrecinctName; //接收科室
    private MessageManage messageManage;
}
