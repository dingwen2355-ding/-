package com.znv.manage.dao;

import com.znv.manage.common.bean.NoticeAnnouncement;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface NoticeAnnouncementDao {

    void deleteNoticeAnnouncement(String[] noticeId);


    void updateNoticeAnnouncement(@Param(value = "noticeId") String noticeId,
                                  @Param(value = "announcementTitle") String announcementTitle,
                                  @Param(value = "announcementType") String announcementType,
                                  @Param(value = "announcementState") String announcementState,
                                  @Param(value = "announcementContent") String announcementContent,
                                  @Param(value = "createTime") String createTime,
                                  @Param(value = "creator") String creator);

    void insertNoticeAnnouncement(@Param(value = "noticeId") String noticeId,
            @Param(value = "announcementTitle") String announcementTitle,
                                  @Param(value = "announcementType") String announcementType,
                                  @Param(value = "announcementState") String announcementState,
                                  @Param(value = "announcementContent") String announcementContent,
                                  @Param(value = "createTime") String createTime,
                                  @Param(value = "creator") String creator);

    List<NoticeAnnouncement> queryNoticeAnnouncement(
            @Param(value = "noticeId") String noticeId,
            @Param(value = "announcementTitle") String announcementTitle,
            @Param(value = "announcementType") String announcementType,
            @Param(value = "announcementState") String announcementState,
            @Param(value = "announcementContent") String announcementContent,
            @Param(value = "createTime") Date createTime,
            @Param(value = "creator") String creator
           );

    List<NoticeAnnouncement> queryNoticeAnnouncementByTitle(
            @Param(value = "announcementTitle") String announcementTitle);

}
