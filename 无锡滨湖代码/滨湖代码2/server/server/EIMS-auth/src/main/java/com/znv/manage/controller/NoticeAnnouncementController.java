package com.znv.manage.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;


import com.znv.manage.common.bean.NoticeAnnouncement;
import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.ResultBean;
import com.znv.manage.common.utils.DateUtils;
import com.znv.manage.service.NoticeAnnouncementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


@Api(tags = "通知公告")
@Controller
@Slf4j
public class NoticeAnnouncementController {
    @Resource
    NoticeAnnouncementService noticeAnnouncementService;


    @ApiOperation("查询通知公告")
    @RequestMapping(value = "/queryNoticeAnnouncement", method = RequestMethod.GET)
    @ResponseBody
    public Result queryNoticeAnnouncement(
            @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(value = "noticeId", required = false) String noticeId,
            @RequestParam(value = "announcementTitle", required = false) String announcementTitle,
            @RequestParam(value = "announcementType", required = false) String announcementType,
            @RequestParam(value = "announcementState", required = false) String announcementState,
            @RequestParam(value = "announcementContent", required = false) String announcementContent,
            @RequestParam(value = "createTime", required = false) Date createTime,
            @RequestParam(value = "creator", required = false) String creator
    ) {
        Result ret = new Result();
        try {
            Page page = PageHelper.startPage(pageNum, pageSize);
            List<NoticeAnnouncement> list = noticeAnnouncementService.queryNoticeAnnouncement(noticeId, announcementTitle, announcementType, announcementState, announcementContent,
                    createTime, creator);
            PageInfo pageInfo = new PageInfo(page);
            if (list.isEmpty()) {
                ret.setMessage("查询结果不存在");
            } else {
                ret.setData(pageInfo);
                ret.setMessage("success");
            }
        } catch (Exception e) {
            ret.setCode(1);
            ret.setMessage(e.getMessage());
            log.error(e.toString());
        }

        return ret;

    }

    @ApiOperation("新增通知公告")
    @RequestMapping(value = "/insertNoticeAnnouncement", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<String> insertNoticeAnnouncement(
            @RequestParam(value = "announcementTitle", required = false) String announcementTitle,
            @RequestParam(value = "announcementType", required = false, defaultValue = "1") String announcementType,
            @RequestParam(value = "announcementState", required = false, defaultValue = "1") String announcementState,
            @RequestParam(value = "announcementContent", required = false) String announcementContent,
            @RequestParam(value = "creator", required = false) String creator
    ) {
        ResultBean<String> ret = new ResultBean<>();

        try {
            noticeAnnouncementService.insertNoticeAnnouncement(null, announcementTitle, announcementType, announcementState, announcementContent,
                    DateUtils.getNowTime("yyyy-MM-dd HH:mm:ss"), creator);

            ret.setResult(ResultBean.SUCESS);
            ret.setRemark("success");
        } catch (Exception e) {
            ret.setResult(ResultBean.FAILED);
            ret.setRemark(e.getMessage());
            log.error(e.toString());

        }
        return ret;

    }

    @ApiOperation("删除通知公告")
    @RequestMapping(value = "/deleteNoticeAnnouncement", method = RequestMethod.DELETE)
    @ResponseBody
    public ResultBean<String> deleteNoticeAnnouncement(
            @RequestParam(value = "noticeId") String noticeId) {
        ResultBean<String> ret = new ResultBean<>();
        try {
            String[] strings = noticeId.split(",");
            noticeAnnouncementService.deleteNoticeAnnouncement(strings);
            ret.setResult(ResultBean.SUCESS);
            ret.setRemark("success");

        } catch (Exception e) {
            ret.setResult(ResultBean.FAILED);
            ret.setRemark(e.getMessage());
            log.error(e.getMessage());
        }
        return ret;
    }


    @ApiOperation("编辑通知公告")
    @RequestMapping(value = "/updateNoticeAnnouncement", method = RequestMethod.PUT)
    @ResponseBody
    public ResultBean<String> updateNoticeAnnouncement(@RequestParam(value = "noticeId") String noticeId,
                                                       @RequestParam(value = "announcementTitle", required = false) String announcementTitle,
                                                       @RequestParam(value = "announcementType", required = false) String announcementType,
                                                       @RequestParam(value = "announcementState", required = false) String announcementState,
                                                       @RequestParam(value = "announcementContent", required = false) String announcementContent,
                                                       @RequestParam(value = "creator", required = false) String creator
    ) {
        ResultBean<String> ret = new ResultBean<>();
        try {
            noticeAnnouncementService.updateNoticeAnnouncement( noticeId, announcementTitle, announcementType, announcementState, announcementContent,
                    DateUtils.getNowTime("yyyy-MM-dd HH:mm:ss"), creator);
            ret.setResult(ResultBean.SUCESS);
            ret.setRemark("success");
        } catch (Exception e) {
            ret.setResult(ResultBean.FAILED);
            ret.setRemark(e.getMessage());
            log.error(e.toString());

        }

        return ret;
    }

    @ApiOperation("通过标题查询通知公告")
    @RequestMapping(value = "/queryNoticeAnnouncementByTitle", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<NoticeAnnouncement> queryNoticeAnnouncementByTitle(
            @RequestParam(value = "announcementTitle") String announcementTitle) {
        ResultBean<NoticeAnnouncement> ret = new ResultBean<>();
        try {
            List<NoticeAnnouncement> list = noticeAnnouncementService
                    .queryNoticeAnnouncementByTitle(announcementTitle
                    );
            ret.addData(list);
            ret.setResult(ResultBean.SUCESS);
            ret.setRemark("success");
        } catch (Exception e) {
            ret.setResult(ResultBean.FAILED);
            ret.setRemark(e.getMessage());
            log.error(e.toString());
        }
        return ret;
    }

}
