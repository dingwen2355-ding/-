package com.ruoyi.binHuServer.controller;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.enums.SendMessageEnum;
import com.ruoyi.system.domain.SysNotice;
import com.ruoyi.system.domain.vo.AuthorityCallBack;
import com.ruoyi.system.service.ISysNoticeService;
import com.ruoyi.system.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Objects;

@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping(value = "/apiCallBack")
@Api(value = "外部应用接口回调")
public class ApiCallBackController {


    private final ISysNoticeService sysNoticeService;

    private final ISysUserService service;

    @ApiOperation("权限申请审批结果回调")
    @PostMapping(value = "/authorityForExamineCallBack")
    public R authorityForExamine( @ApiParam @RequestBody AuthorityCallBack authorityCallBack) {
        log.error("收到 权限申请审批结果回调,参数是:  {}",authorityCallBack);
        SysUser applyUser = service.selectUserByUserName(authorityCallBack.getApplyId());
        if (applyUser==null){
            log.error("权限申请审批结果回调,查询不到申请用户");
            return R.ok();
        }
        SendMessageEnum sendMessageEnum=null;
        if (authorityCallBack.getStatus()==0){
            sendMessageEnum=SendMessageEnum.authoritySuccess;
        }else if (authorityCallBack.getStatus()==2){
            sendMessageEnum=SendMessageEnum.authorityFail;
        }else if (authorityCallBack.getStatus()==4){
            sendMessageEnum=SendMessageEnum.authorityWithdraw;
        }else {
            return R.fail("状态错误");
        }
        // 给申请用户发消息
        sysNoticeService.insertNotice(
                SysNotice.getAuthorityMessageDetail(sendMessageEnum,applyUser.getUserId(),authorityCallBack.getApplyUserName(),authorityCallBack.getAppName()));
        return R.ok();
    }
}
