package com.znv.manage.common.bean.tip;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class MessageManage {
    private String messageId; //消息id
    private String messageContent; //消息详情
    private String messageTitle; //消息标题
    private String sendPerson; //发送人
    private String sendPrecinctName; //发送人科室
    private String sendPhone; //发送人联系方式
    private String imgUrl; // 文件上传
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date sendTime; //发送时间

    private String messageLevel; //消息级别
    private String messageCategory; //消息类别

    private List<MessagePrecinctBean> messagePrecinctBeans = new ArrayList<>();

    public MessageManage() {
        super();
    }

    public MessageManage(
            String messageId,
            String messageContent,
            String messageTitle,
            String sendPerson,
            String sendPrecinctName,
            String sendPhone,
            String imgUrl,
            String messageLevel,
            String messageCategory
    ) {
        this.messageId = messageId;
        this.messageContent = messageContent;
        this.messageTitle = messageTitle;
        this.sendPerson = sendPerson;
        this.sendPrecinctName = sendPrecinctName;
        this.sendPhone = sendPhone;
        this.imgUrl = imgUrl;
        this.messageLevel = messageLevel;
        this.messageCategory = messageCategory;
    }




}
