package com.znv.manage.common.bean;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SyncEventContent {

    //事件id
    private String flowNo;

    private String operationContent;

    private String fromNodeId;

    private String orderNodeId;

    private String createDate;


}
