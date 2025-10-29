package com.jslc.modules.job.task;

import com.alibaba.fastjson.JSONObject;
import com.jslc.modules.szbh.service.ISmsSendSettingService;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springblade.core.tool.utils.Func;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author zhengyongbiao
 * @title: Tesk
 * @projectName digit-binhu-back
 * @description:
 * @date 2023/9/13 17:12
 */
@Slf4j
@Component("sendMsmTask")
public class SendMsmTask extends AbstractTask{

    @Autowired
    private ISmsSendSettingService smsSendSettingService;

    @Override
    public void handler(String params) {
        log.info("-----------------------------------执行业务开始："+ LocalDateTime.now());

        if(Func.isNotEmpty(params)){
            JSONObject jsonObject = JSONObject.parseObject(params);
            String mobile = jsonObject.getString("mobile");
            String content = jsonObject.getString("content");
            Long bizId = jsonObject.getLong("bizId");

            smsSendSettingService.sendMsg(null, mobile, content, bizId);
        }else{
            log.info("-----------------------------------执行业务失败："+ LocalDateTime.now() + "，params:" + params);
        }
        log.info("-----------------------------------执行业务结束："+ LocalDateTime.now());
    }
}
