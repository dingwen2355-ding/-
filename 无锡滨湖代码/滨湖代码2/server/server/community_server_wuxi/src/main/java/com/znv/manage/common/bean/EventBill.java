package com.znv.manage.common.bean;

import lombok.Data;

/**
 * @author 0049003788
 */
@Data
public class EventBill {
    private Integer id;

    private String incomingFlowNo;

    private String questionTitle;

    private String billJson;

    private String departments;

    private String eventId;
}
