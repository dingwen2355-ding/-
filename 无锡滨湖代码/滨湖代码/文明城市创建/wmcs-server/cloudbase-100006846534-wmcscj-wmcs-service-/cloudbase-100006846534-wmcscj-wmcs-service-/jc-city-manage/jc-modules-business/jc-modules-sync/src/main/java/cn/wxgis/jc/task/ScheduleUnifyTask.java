package cn.wxgis.jc.task;

import cn.wxgis.jc.sync.web.service.SynchUnifyService;
import cn.wxgis.jc.sync.web.service.SynchWmbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 同步统一身份认证的方法
 */
@Configuration
@EnableScheduling
public class ScheduleUnifyTask {

    @Autowired
    private SynchUnifyService unifyService;


    @Scheduled(cron = "0 1 * * 1 *")
    private void unifyDeptTasks() {
        System.out.println("==========同步统一身份认证部门信息 ==== start ");
        unifyService.synchDeptData(0);
        System.out.println("==========同步统一身份认证部门信息 ==== end ");
    }

    @Scheduled(cron = "0 1 * * 1 *")
    private void unifyUserTasks() {
        System.out.println("==========同步统一身份认证用户信息 ==== start ");
        unifyService.synchUserData(null);
        System.out.println("==========同步统一身份认证用户信息 ==== end ");
    }

    @Scheduled(cron = "0 2 * * 1 *")
    private void updateUnifyToSysUserTasks() {
        System.out.println("==========测试同步更新数据至用户表 ==== start ");
        unifyService.updateUnifyToSysUser();
        System.out.println("==========测试同步更新数据至用户表 ==== end ");
    }

}
