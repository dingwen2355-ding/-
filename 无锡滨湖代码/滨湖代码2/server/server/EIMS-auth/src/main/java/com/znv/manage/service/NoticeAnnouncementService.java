package com.znv.manage.service;

import com.znv.manage.common.bean.NoticeAnnouncement;

import java.util.Date;
import java.util.List;

public interface NoticeAnnouncementService {

    void deleteNoticeAnnouncement(String[] noticeId);

    void insertNoticeAnnouncement( String noticeId,
            String announcementTitle,
                                   String announcementType,
                                   String announcementState,
                                   String announcementContent,
                                   String createTime,
                                   String creator);

    void updateNoticeAnnouncement(String noticeId,
                                  String announcementTitle,
                                  String announcementType,
                                  String announcementState,
                                  String announcementContent,
                                  String createTime,
                                  String creator);

    List<NoticeAnnouncement> queryNoticeAnnouncement(
            String noticeId,
            String announcementTitle,
            String announcementType,
            String announcementState,
            String announcementContent,
            Date createTime,
            String creator
            );
    List<NoticeAnnouncement> queryNoticeAnnouncementByTitle(String announcementTitle);

}
