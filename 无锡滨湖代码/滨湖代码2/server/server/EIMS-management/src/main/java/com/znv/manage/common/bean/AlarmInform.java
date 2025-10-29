package com.znv.manage.common.bean;

import lombok.Data;

/**
 * @author yangbo
 * @email
 * @date 2022/8/12
 */
@Data
public class AlarmInform {

    private String alarmId;
    private String eventId;
    private String announcementTitle;
    private String announcementType;
    private String announcementContent;
    private String pushDepartmentNumber;
    private String pushPersonNumber;
    private String creator;
    private String isAccept;
    private String remark;
}
