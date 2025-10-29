package com.znv.manage.common.bean;

import lombok.Data;
import org.springframework.util.StringUtils;

@Data
public class NoticeAnnouncement {
    private String noticeId;
    private String announcementTitle;
    private String announcementType;  // 1 通知 , 2 公告
    private String announcementState;  // 1 正常 , 2 关闭
    private String announcementContent;
    private String createTime;
    private String creator;

    public NoticeAnnouncement() {
    }

    public NoticeAnnouncement(String noticeId,
                              String announcementTitle,
                              String announcementType,
                              String announcementState,
                              String announcementContent,
                              String createTime,
                              String creator)
    {
        this.noticeId = noticeId;
        this.announcementTitle = announcementTitle;
        this.announcementType = announcementType;
        this.announcementState = announcementState;
        this.announcementContent = announcementContent;
        this.createTime = createTime;
        this.creator = creator;
    }

    public String getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(String noticeId) {
        this.noticeId = noticeId;
    }

    public String getCreateTime() {
        if (!StringUtils.isEmpty(this.createTime) && this.createTime.contains(".0")) {
            return this.createTime.replace(".0","");
        } else {
            return this.createTime;
        }
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
