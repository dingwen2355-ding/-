package com.znv.manage.task;

import com.znv.manage.common.utils.DateUtil;
import com.znv.manage.service.impl.DaHuaVideoServiceImpl;
import com.znv.manage.service.impl.DahuaServiceImpl;
import com.znv.manage.service.impl.DeviceServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;


@Slf4j
@Component
public class DeviceTask {

    @Autowired
    private DaHuaVideoServiceImpl daHuaVideoService;

    @Resource
    private DeviceServiceImpl deviceService;

    @Resource
    private DahuaServiceImpl dahuaService;

    @Scheduled(initialDelay = 24 * 60 * 60 * 1000, fixedRate = 24 * 60 * 60 * 1000)
    @Async
    public void Run() {
        log.info("start DeviceTask...");

        deviceService.saveHikDevice();

        log.info("end DeviceTask...");

    }

    /**
     * 城市之眼设备同步
     */
//     @Scheduled(cron = "0 0 1 * * ? ")
//     @Async
//     public void syncCityEyesDevice(){
//         log.info("start SyncCityEyesDevice...");
//
//         daHuaVideoService.SyncCityEyesDevice();
//
//         log.info("end SyncCityEyesDevice...");
//
//     }

//     /**
//      * 青白江区单兵
//      */
//     @Scheduled(initialDelay = 5*1000, fixedRate = 60 * 1000)
//     @Async
//     public void Run(){
//         log.info("start updateDanBinGps...");
//
//         deviceService.updateDanBinGps();
//
//         log.info("end updateDanBinGps...");
//
//     }
//
//     /**
//      * 龙泉驿区单兵
//      */
//     @Scheduled(initialDelay = 5*1000, fixedRate = 60 * 1000)
//     @Async
//     public void LongQuanDanbin(){
//         log.info("start LongQuanDanbin...");
//
// //        dahuaService.updateGps();
//         dahuaService.dealLqyDanbinEventMessage();
//
//         log.info("end LongQuanDanbin...");
//
//     }
//
    /**
     * 温江区单兵坐标同步
     */
    @Scheduled(initialDelay = 5*1000, fixedRate = 60 * 1000)
    @Async
    public void WenJiangDanbin(){
        log.info("start WenJiangDanbin...");

        deviceService.updateWenJiangDanBin();

        log.info("end WenJiangDanbin...");

    }

    /**
     * 单兵设备同步
     */
    @Scheduled(cron = "0 0 2 * * ?")
    @Async
    public void syncDbEquip(){
        deviceService.syncDbEquip();
    }

    @Scheduled(cron = "0 0 2 * * ?")
    @Async
    public void dahuaDeviceSync(){
        log.info("start dahuaDeviceSync...");

        dahuaService.syncDevice();

        log.info("end dahuaDeviceSync...");

    }
//
//     /**
//      * 成都东部新区单兵
//      */
//     @Scheduled(initialDelay = 5*1000, fixedRate = 60 * 1000)
//     @Async
//     public void updateDongbuDanBin(){
//         log.info("start updateDongbuDanBin...");
//
//         deviceService.updateDongbuDanBin();
//
//         log.info("end updateDongbuDanBin...");
//
//     }
//
//     /**
//      * 新津区单兵
//      */
//     @Scheduled(initialDelay = 5*1000, fixedRate = 60 * 1000)
//     @Async
//     public void updateXinjinDanBin(){
//         log.info("start updateXinjinDanBin...");
//
//         deviceService.updateXinjinDanBin();
//
//         log.info("end updateXinjinDanBin...");
//
//     }
//
//     /**
//      * 新都区单兵
//      */
//     @Scheduled(initialDelay = 5*1000, fixedRate = 60 * 1000)
//     @Async
//     public void updateXinduDanBin(){
//         log.info("start updateXinduDanBin...");
//
//         deviceService.updateXinduDanBin();
//
//         log.info("end updateXinduDanBin...");
//
//     }
//
//     /**
//      * 崇州区单兵
//      */
//     @Scheduled(initialDelay = 5*1000, fixedRate = 60 * 1000)
//     @Async
//     public void ChongZhouDanbin(){
//         log.info("start ChongZhouDanbin...");
//
//         deviceService.saveChongzhouDanbin();
//
//         log.info("end ChongZhouDanbin...");
//
//     }
//
//     /**
//      * 高新区单兵
//      */
//     @Scheduled(initialDelay = 5*1000, fixedRate = 60 * 1000)
//     @Async
//     public void GaoxinDanbin(){
//         log.info("start saveGaoxinDanbin...");
//
//         deviceService.saveGaoxinDanbin();
//
//         log.info("end saveGaoxinDanbin...");
//
//     }
//
//     /**
//      * 威尔信单兵
//      */
//     @Scheduled(initialDelay = 5*1000, fixedRate = 60 * 1000)
//     @Async
//     public void saveWEXDanbin(){
//         log.info("start saveWEXDanbin...");
//
//         deviceService.saveWEXDanbin();
//
//         log.info("end saveWEXDanbin...");
//
//     }
//
//     /**
//      * 双流区单兵
//      */
//     @Scheduled(initialDelay = 5*1000, fixedRate = 60 * 1000)
//     @Async
//     public void ShuangliuDanbin(){
//         log.info("start ShuangliuDanbin...");
//
//         deviceService.saveShuangliuDanbin();
//
//         log.info("end ShuangliuDanbin...");
//
//     }
//
//
//     /**
//      * 消防车同步
//      */
//     @Scheduled(initialDelay = 5*1000, fixedRate = 10 * 1000)
//     @Async
//     public void saveXFC(){
//         log.info("start saveXFC...");
//
//         deviceService.saveXFC();
//
//         log.info("end saveXFC...");
//
//     }
//
//     /**
//      * 无人机同步
//      */
//     @Scheduled(initialDelay = 5*1000, fixedRate = 60 * 1000)
//     @Async
//     public void saveWRJ(){
//         log.info("start saveWRJ...");
//
//         deviceService.saveWRJ();
//
//         log.info("end saveWRJ...");
//
//     }
}
