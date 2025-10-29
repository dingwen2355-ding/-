package cn.wxgis.jc.task;

import cn.wxgis.jc.civilization.data.po.DataCheckPointsType;
import cn.wxgis.jc.civilization.data.resp.DataCheckPointsResponse;
import cn.wxgis.jc.civilization.work.enums.ThresholdFightFlagEnum;
import cn.wxgis.jc.civilization.work.enums.ThresholdTypeEnum;
import cn.wxgis.jc.civilization.work.po.WorkThreshold;
import cn.wxgis.jc.common.constant.SecurityConstants;
import cn.wxgis.jc.common.enums.common.FlagEnum;
import cn.wxgis.jc.common.util.BeanUtils;
import cn.wxgis.jc.common.util.DateUtils;
import cn.wxgis.jc.common.util.StringUtils;
import cn.wxgis.jc.influx.service.InfluxdbService;
import cn.wxgis.jc.synch.common.constant.InfluxSql;
import cn.wxgis.jc.task.mapper.DataCheckPointsDeviceMapper;
import cn.wxgis.jc.task.mapper.DataCheckPointsTypeMapper;
import cn.wxgis.jc.task.service.WfEventReportService;
import cn.wxgis.jc.task.service.WorkThresholdService;
import cn.wxgis.jc.workflow.fegin.RemoteEventReportService;
import cn.wxgis.jc.workflow.po.WfEventReport;
import cn.wxgis.jc.workflow.vo.WmcsWarnVo;
import org.influxdb.dto.QueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 定时方法读取influx数据
 */
@Configuration
@EnableScheduling
public class ScheduleTask {

    @Autowired
    RemoteEventReportService remoteEventReportService;
    @Autowired
    private InfluxdbService influxdbService;
    @Autowired
    private WorkThresholdService workThresholdService;
    @Autowired
    private WfEventReportService eventReportService;
    @Autowired
    private DataCheckPointsTypeMapper dataCheckPointsTypeMapper;
    @Autowired
    private DataCheckPointsDeviceMapper dataCheckPointsDeviceMapper;

    @Scheduled(cron = "* 0/1 * * * ?")
    private void configureTasks() {
        // 查询最新一条数据
        QueryResult query = influxdbService.query(String.format(InfluxSql.newDataSql));
        List<Map<String, Object>> maps = influxdbService.queryResultProcess(query);
        if (StringUtils.isEmpty(maps)) return;
        Map<String, Object> map = maps.get(0);
        WmcsWarnVo wmcsWarnVo = BeanUtils.map2bean(map, WmcsWarnVo.class);
        if (StringUtils.isNull(wmcsWarnVo)) return;


        DataCheckPointsResponse checkPoints = dataCheckPointsDeviceMapper.findResponseByDeviceId(wmcsWarnVo.getChannelId());
        if (StringUtils.isNotNull(checkPoints)) {
            DataCheckPointsType checkPointsType = dataCheckPointsTypeMapper.selectById(checkPoints.getTypeId());
            if (StringUtils.isNull(checkPointsType)) return;
            if (checkPointsType.getType().equals(0)) return;
            if (checkPointsType.getType().equals(2)) {
                // 判断当前扫描时间是否在该事件点位类型扫描范围中
                // 如果在扫描范围中，执行下一步扫描判断工单是否结束
                // 如果不在扫描范围中，直接跳过
                String start = StringUtils.isNotBlank(checkPointsType.getStartTime()) ? checkPointsType.getStartTime() : "08:00:00";
                String finish = StringUtils.isNotBlank(checkPointsType.getFinishTime()) ? checkPointsType.getFinishTime() : "17:00:00";

                Date nowTime = DateUtils.parseStringToDate(DateUtils.parseDateToString(new Date(), DateUtils.HH_MM_SS), DateUtils.HH_MM_SS);
                if (!DateUtils.isEffectiveDate(nowTime, DateUtils.parseStringToDate(start, DateUtils.HH_MM_SS), DateUtils.parseStringToDate(finish, DateUtils.HH_MM_SS))) {
                    return;
                }
            }
            WorkThreshold workThreshold = workThresholdService.findByType(wmcsWarnVo.getType());
            if (StringUtils.isNull(workThreshold)) return;
            Integer count = 1;
            if (workThreshold.getType().equals(ThresholdTypeEnum.HOUR.getCode())) {
                count = 60;
            } else if (workThreshold.getType().equals(ThresholdTypeEnum.DAY.getCode())) {
                count = 1440;
            }
            wmcsWarnVo.setMinute(count);
            // 查询此流程根据配置的最后的数据
            QueryResult queryByThreshold = influxdbService.query(String.format(InfluxSql.timeSql, count, wmcsWarnVo.getChannelId()));
            List<Map<String, Object>> queryByThresholdList = influxdbService.queryResultProcess(queryByThreshold);
            if (StringUtils.isEmpty(queryByThresholdList)) return;
            String overCount = workThreshold.getNormalOverCount();
            if (workThreshold.getSpecialFlag().equals(ThresholdFightFlagEnum.TRUE.getCode())) {
                overCount = workThreshold.getOverCount();
            }
            if (queryByThresholdList.size() > Long.parseLong(overCount)) {
                boolean exist = eventReportService.existByWarnInfo(wmcsWarnVo);
                if (exist) return;
                remoteEventReportService.createWorkflow(wmcsWarnVo, SecurityConstants.INNER);
            }
        }
    }


    @Scheduled(cron = "* 0/1 * * * ?")
    private void finishEventTasks() {
        List<WfEventReport> list = eventReportService.listNoEndByAi();
        if (StringUtils.isEmpty(list)) return;
        list.forEach(r -> {
            // 获取当前事件的点位类型
            DataCheckPointsType checkPointsType = dataCheckPointsTypeMapper.selectById(r.getCheckPointsTypeId());
            if (StringUtils.isNull(checkPointsType)) return;
            if (checkPointsType.getType().equals(0)) return;
            if (checkPointsType.getType().equals(2)) {
                // 判断当前扫描时间是否在该事件点位类型扫描范围中
                // 如果在扫描范围中，执行下一步扫描判断工单是否结束
                // 如果不在扫描范围中，直接跳过
                String start = StringUtils.isNotBlank(checkPointsType.getStartTime()) ? checkPointsType.getStartTime() : "08:00:00";
                String finish = StringUtils.isNotBlank(checkPointsType.getFinishTime()) ? checkPointsType.getFinishTime() : "17:00:00";

                Date nowTime = DateUtils.parseStringToDate(DateUtils.parseDateToString(new Date(), DateUtils.HH_MM_SS), DateUtils.HH_MM_SS);
                if (!DateUtils.isEffectiveDate(nowTime, DateUtils.parseStringToDate(start, DateUtils.HH_MM_SS), DateUtils.parseStringToDate(finish, DateUtils.HH_MM_SS))) {
                    return;
                }
            }

            WorkThreshold workThreshold = workThresholdService.findByType(r.getWarnType());
            Integer count = 1;
            if (workThreshold.getType().equals(ThresholdTypeEnum.HOUR.getCode())) {
                count = 60;
            } else if (workThreshold.getType().equals(ThresholdTypeEnum.DAY.getCode())) {
                count = 1440;
            }
            String overCount = workThreshold.getNormalOverCount();
            if (workThreshold.getSpecialFlag().equals(FlagEnum.TRUE.getCode())) {
                overCount = workThreshold.getOverCount();
            }
             // 获取生成工单平均条数；公式为 分钟/告警条数
            BigDecimal num = new BigDecimal(count).divide(new BigDecimal(overCount)).add(new BigDecimal(300));
            QueryResult queryByThreshold = influxdbService.query(String.format(InfluxSql.timeSql, num.intValue(), r.getChannelId()));
            List<Map<String, Object>> queryByThresholdList = influxdbService.queryResultProcess(queryByThreshold);
//            long checkOverCount = StringUtils.isNull(overCount) ? 0 : workThreshold.getNormalOverCount().longValue();
            if (StringUtils.isEmpty(queryByThresholdList) || queryByThresholdList.size() <= 0) {
                remoteEventReportService.finishWorkflow(r.getFlowinstanceid(), SecurityConstants.INNER);
            }
        });
    }

}
