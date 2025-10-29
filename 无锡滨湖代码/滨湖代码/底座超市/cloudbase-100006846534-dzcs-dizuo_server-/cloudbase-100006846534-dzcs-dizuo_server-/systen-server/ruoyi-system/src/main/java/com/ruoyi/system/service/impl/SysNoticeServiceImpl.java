package com.ruoyi.system.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.customer.domain.CmDuanXinSwitch;
import com.ruoyi.system.customer.service.CmDuanXinSwitchService;
import com.ruoyi.system.customer.service.RequestService;
import com.ruoyi.system.service.ISysUserService;
import io.jsonwebtoken.lang.Collections;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.SysNotice;
import com.ruoyi.system.mapper.SysNoticeMapper;
import com.ruoyi.system.service.ISysNoticeService;

/**
 * 公告 服务层实现
 *
 * @author ruoyi
 */
@Service
@Slf4j
public class SysNoticeServiceImpl implements ISysNoticeService
{
    @Autowired
    private SysNoticeMapper noticeMapper;

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private CmDuanXinSwitchService cmDuanXinSwitchService;
    @Autowired
    private RequestService requestService;
    /**
     * 查询公告信息
     *
     * @param noticeId 公告ID
     * @return 公告信息
     */
    @Override
    public SysNotice selectNoticeById(Long noticeId)
    {
        SysNotice sysNotice = noticeMapper.selectNoticeById(noticeId);
        if (getCanRead(sysNotice)) {
            sysNotice.setRead("1");
            updateNotice(sysNotice);
        }
        return noticeMapper.selectNoticeById(noticeId);
    }

    /**
     * 查询公告列表
     *
     * @param notice 公告信息
     * @return 公告集合
     */
    @Override
    public List<SysNotice> selectNoticeList(SysNotice notice)
    {
        if (SecurityUtils.isAdmin()) {
            notice.setNoticePerson(1L);
        }else {
            notice.setNoticePerson(SecurityUtils.getUserId());
        }
        return noticeMapper.selectNoticeList(notice);
    }

    /**
     * 新增公告
     *
         * @param notice 公告信息
     * @return 结果
     */
    @Override
    public int insertNotice(SysNotice notice)
    {
        notice.setCreateBy(notice.getNoticePerson().toString());
        notice.setNoticePerson(notice.getNoticePerson());

        log.error("组装SysNotice。。后：{}",notice);

        int num = 0;
        try {
            log.error("....");
            num = noticeMapper.insertNotice(notice);
            //非管理员给全部管理员发发短信 管理员则不发
            log.error("SecurityUtils.isAdmin():  {}",SecurityUtils.isAdmin() );
            if (!SecurityUtils.isAdmin())
            {
                try {
                    List<CmDuanXinSwitch> list = cmDuanXinSwitchService.list();

                    log.error("List<CmDuanXinSwitch>:  {}",list );
                    int status = list.get(0).getStatus();
                    if (status==0){
                        log.error("getAdminUsersPhoneNumber():  {}",getAdminUsersPhoneNumber() );
                        requestService.sendMessage(notice.getNoticeContent(),getAdminUsersPhoneNumber());
                    }
                }catch (Exception e){
                    log.error("获取短信开关失败！");
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return num;
    }

    /**
     * 修改公告
     *
     * @param notice 公告信息
     * @return 结果
     */
    @Override
    public int updateNotice(SysNotice notice)
    {
        return noticeMapper.updateNotice(notice);
    }

    /**
     * 删除公告对象
     *
     * @param noticeId 公告ID
     * @return 结果
     */
    @Override
    public int deleteNoticeById(Long noticeId)
    {
        return noticeMapper.deleteNoticeById(noticeId);
    }

    /**
     * 批量删除公告信息
     *
     * @param noticeIds 需要删除的公告ID
     * @return 结果
     */
    @Override
    public int deleteNoticeByIds(Long[] noticeIds)
    {
        return noticeMapper.deleteNoticeByIds(noticeIds);
    }

    private String[] getAdminUsersPhoneNumber()
    {
    return sysUserService.selectAdminUsers().stream()
            .map(SysUser::getPhonenumber)
            .filter(Objects::nonNull)
            .toArray(String[]::new);
    }

    boolean getCanRead(SysNotice sysNotice){
        //如果公告人为1则所有管理员都有已读权限,当前用户是公告人也有已读权限
        return (sysNotice.getNoticePerson().equals(1L) && SecurityUtils.isAdmin())
                ||(SecurityUtils.getUserId().equals(sysNotice.getNoticePerson()));
    }
}
