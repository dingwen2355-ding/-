package com.znv.manage.task;

import com.znv.manage.common.bean.information.MesReviewBean;
import com.znv.manage.common.utils.DateUtil;
import com.znv.manage.dao.information.MesManageDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 信息发布,状态巡检
 *
 * @author chd
 * @date 2022/4/29
 */
@Component
@Slf4j
public class MesStatusTask {

    @Resource
    MesManageDao mesManageDao;

    /**
     * 信息发布,状态巡检
     */
    @Scheduled(cron = "0 0 */1 * * ?")
    @Async
    public void mesStatusChecking() {
        try {
            MesReviewBean mesReviewBean = new MesReviewBean();
            mesReviewBean.setRelId("1");
            mesReviewBean.setRelName("测试");
            mesReviewBean.setAdviceId("test");
            mesReviewBean.setAdviceName("test");
            mesReviewBean.setReviewTime(DateUtil.getStringDate());
            mesReviewBean.setStatus("0");
            mesReviewBean.setRemark("ok");
            mesManageDao.addMesReview(mesReviewBean);
        } catch (Exception e) {
            log.error("mesStatusChecking is fail. e:{}", e);
        }
        log.info("mesStatusChecking is end...");
    }
}
