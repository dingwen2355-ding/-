package com.ruoyi.system.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.enums.SendMessageEnum;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.customer.domain.CmAbility;
import com.ruoyi.system.customer.domain.CmApp;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.xss.Xss;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

import static com.ruoyi.common.enums.SendMessageEnum.cmAbilityCreate_new;
import static com.ruoyi.common.utils.DateUtils.YYYYMMDDHHMMSS;

/**
 * 通知公告表 sys_notice
 *
 * @author ruoyi
 */
public class SysNotice extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(SysNotice.class);

    /**
     * 公告ID
     */
    @TableId(value = "notice_id", type = IdType.ASSIGN_ID)
    private Long noticeId;

    /**
     * 公告标题
     */
    private String noticeTitle;

    /**
     * 公告类型（1通知 2公告）
     */
    private String noticeType;

    /**
     * 公告内容
     */
    private String noticeContent;

    /**
     * 公告状态（0正常 1关闭）
     */
    private String status;

    public void setNoticePerson(Long noticePerson) {
        this.noticePerson = noticePerson;
    }

    public void setRead(String read) {
        this.read = read;
    }

    public Long getNoticePerson() {
        return noticePerson;
    }

    public String getRead() {
        return read;
    }

    private Long noticePerson;

    private String read;


    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    private String nickName;

    public Long getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(Long noticeId) {
        this.noticeId = noticeId;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    @Xss(message = "公告标题不能包含脚本字符")
    @NotBlank(message = "公告标题不能为空")
    @Size(min = 0, max = 50, message = "公告标题不能超过50个字符")
    public String getNoticeTitle() {
        return noticeTitle;
    }

    public void setNoticeType(String noticeType) {
        this.noticeType = noticeType;
    }

    public String getNoticeType() {
        return noticeType;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }

    public String getNoticeContent() {
        return noticeContent;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("noticeId", getNoticeId())
                .append("noticeTitle", getNoticeTitle())
                .append("noticeType", getNoticeType())
                .append("noticeContent", getNoticeContent())
                .append("status", getStatus())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .append("noticePerson", getNoticePerson())
                .append("read", getRead())
                .toString();
    }


    public static SysNotice getCmAbilityMessageDetail(SendMessageEnum sendMessageEnum, CmAbility cmAbility, SysUser sysUser, Date time) {
        SysNotice sysNotice = new SysNotice() {{
            setNoticeTitle("能力申请");
        }};

        switch (sendMessageEnum) {
            case cmAbilityFail:
                sysNotice.setNoticePerson(getLongFromString(cmAbility.getCreateBy()));
                sysNotice.setNoticeContent(String.format(sendMessageEnum.toString(), sysUser.getNickName(), cmAbility.getAbilityList()));
                break;

            case cmAbilitySuccess_new:
            case cmAbilityWithdraw_new:
            case cmAbilityCreate_new:
                sysNotice.setNoticePerson(getLongFromString(cmAbility.getCreateBy()));
                sysNotice.setNoticeContent(String.format(sendMessageEnum.toString(),  sysUser.getNickName(), cmAbility.getAbilityList()));
                break;
        }
        return sysNotice;
    }

    public static SysNotice getCmAppMessageDetail(SendMessageEnum sendMessageEnum, CmApp cmApp, SysUser sysUser, Date time) {
        SysNotice sysNotice = new SysNotice() {{
            setNoticeTitle("入驻申请");
        }};
        switch (sendMessageEnum) {


            case cmAppCreate_new:
            case cmAppWithdraw_new:
            case cmAbilitySuccess_new:
                sysNotice.setNoticePerson(getLongFromString(cmApp.getCreateBy()));
                sysNotice.setNoticeContent(String.format(sendMessageEnum.toString(),  sysUser.getNickName(), cmApp.getAppName()));
                break;
            case cmAppCreate:
            case cmAppWithdraw:
                sysNotice.setNoticePerson(getLongFromString(cmApp.getCreateBy()));
                sysNotice.setNoticeContent(String.format(sendMessageEnum.toString(), sysUser.getNickName(), cmApp.getAppName()));
                break;
            case cmAppSuccess:
            case cmAppFail:
                sysNotice.setNoticePerson(getLongFromString(cmApp.getCreateBy()));
                sysNotice.setNoticeContent(String.format(sendMessageEnum.toString(), sysUser.getNickName(), cmApp.getAppName()));
                break;
        }
        return sysNotice;
    }
    public static SysNotice getCmAppMessageDetailForAuthApply(SendMessageEnum sendMessageEnum, String appName,String noticePerson, SysUser sysUser, Date time) {
        SysNotice sysNotice = new SysNotice() {{
            setNoticeTitle("权限申请");
        }};
        switch (sendMessageEnum) {
            case authorityCreate:
                sysNotice.setNoticePerson(getLongFromString(noticePerson));
                sysNotice.setNoticeContent(String.format(sendMessageEnum.toString(), sysUser.getNickName(), appName));
                break;
        }
        return sysNotice;
    }

    public static SysNotice getAuthorityMessageDetail(SendMessageEnum sendMessageEnum, Long noticePerson, String userName,String appName) {
        SysNotice sysNotice = new SysNotice(){{setNoticeTitle("权限申请");}};
        sysNotice.setNoticePerson(noticePerson);
        sysNotice.setNoticeContent(String.format(sendMessageEnum.toString(), userName, appName));
        log.error("组装SysNotice前：{}",sysNotice);
        return sysNotice;
    }


    public static Long getLongFromString(String num) {
        return StringUtils.isNumeric(num) ? Long.parseLong(num) : null;
    }

    public static void main(String[] args) {
        System.out.println(String.format(cmAbilityCreate_new.toString(),  "sysUser.getNickName()", "cmAbility.getAbilityList()"));
    }
}
