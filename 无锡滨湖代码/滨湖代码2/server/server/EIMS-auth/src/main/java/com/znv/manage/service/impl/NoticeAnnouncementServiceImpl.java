package com.znv.manage.service.impl;

import com.znv.manage.common.bean.NoticeAnnouncement;
import com.znv.manage.dao.NoticeAnnouncementDao;
import com.znv.manage.service.NoticeAnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class NoticeAnnouncementServiceImpl implements NoticeAnnouncementService {
    @Autowired
    NoticeAnnouncementDao noticeAnnouncementDao;


    @Override
    public void deleteNoticeAnnouncement(String[]  noticeId) {
        noticeAnnouncementDao.deleteNoticeAnnouncement(noticeId);
    }

    @Override
    public void insertNoticeAnnouncement(String noticeId, String announcementTitle, String announcementType,
                                         String announcementState, String announcementContent,String createTime, String creator) {
        noticeAnnouncementDao.insertNoticeAnnouncement(noticeId,announcementTitle,announcementType,announcementState,announcementContent,createTime,creator);
    }

    @Override
    public void updateNoticeAnnouncement(String noticeId, String announcementTitle, String announcementType,
                                         String announcementState, String announcementContent,String createTime, String creator) {
        noticeAnnouncementDao.updateNoticeAnnouncement(noticeId,announcementTitle,announcementType,announcementState,announcementContent,createTime,creator);
    }



    @Override
    public List<NoticeAnnouncement> queryNoticeAnnouncement(String noticeId, String announcementTitle, String announcementType,
                                                            String announcementState, String announcementContent, Date createTime, String creator) {
        return noticeAnnouncementDao.queryNoticeAnnouncement(noticeId,announcementTitle,announcementType,announcementState,announcementContent,createTime,creator);
    }

    @Override
    public List<NoticeAnnouncement> queryNoticeAnnouncementByTitle(String announcementTitle) {
        return noticeAnnouncementDao.queryNoticeAnnouncementByTitle(announcementTitle);
    }


}
