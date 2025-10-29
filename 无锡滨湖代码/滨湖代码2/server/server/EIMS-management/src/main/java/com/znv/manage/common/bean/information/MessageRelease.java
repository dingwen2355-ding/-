package com.znv.manage.common.bean.information;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class MessageRelease {
    private Long id;

    private String messageTitle;

    private String messageType1;

    private String messageType2;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date releaseTime;

    private String messageContent;
}