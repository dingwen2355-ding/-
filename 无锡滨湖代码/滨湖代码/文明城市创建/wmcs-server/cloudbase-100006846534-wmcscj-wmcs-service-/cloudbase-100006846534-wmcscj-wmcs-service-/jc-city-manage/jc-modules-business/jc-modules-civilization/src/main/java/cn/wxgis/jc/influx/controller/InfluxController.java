package cn.wxgis.jc.influx.controller;

import cn.wxgis.jc.civilization.data.po.DataDeviceChannel;
import cn.wxgis.jc.civilization.data.service.CheckPointsDeviceService;
import cn.wxgis.jc.civilization.data.service.DeviceChannelService;
import cn.wxgis.jc.civilization.data.vo.ChannelVo;
import cn.wxgis.jc.civilization.work.select.WorkWarnSelect;
import cn.wxgis.jc.civilization.work.vo.WarnCountVo;
import cn.wxgis.jc.common.result.JsonResult;
import cn.wxgis.jc.common.result.PageBean;
import cn.wxgis.jc.common.util.DateUtils;
import cn.wxgis.jc.common.util.StringUtils;
import cn.wxgis.jc.synch.common.constant.InfluxSql;
import cn.wxgis.jc.influx.service.InfluxdbService;
import cn.wxgis.jc.system.po.SysDictData;
import cn.wxgis.jc.system.service.SysDictDataService;
import cn.wxgis.jc.workflow.vo.WmcsWarnVo;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.influxdb.dto.QueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/workWarn")
public class InfluxController {

    @Autowired
    private InfluxdbService influxdbService;
    @Autowired
    private DeviceChannelService deviceChannelService;
    @Autowired
    private CheckPointsDeviceService checkPointsDeviceService;
    @Autowired
    private SysDictDataService dictDataService;

    @ApiOperation(value ="告警信息分页", httpMethod ="POST")
    @PostMapping("/page")
    public JsonResult page(@RequestBody WorkWarnSelect select) {
        // 查询分页数据
        String selectParam = "where 1=1";
        if (StringUtils.isNotBlank(select.getType())) {
            selectParam += " and type = '" + select.getType() + "'";
        }
        if (StringUtils.isNotBlank(select.getChannelId())) {
            selectParam += " and channelId =~ /" + select.getChannelId() + "/";
        }
        if (StringUtils.isNotBlank(select.getInfo())) {
            selectParam += " and content =~ /" + select.getInfo() + "/";
        }
        QueryResult countQuery = influxdbService.query(String.format(InfluxSql.countSql, selectParam));
        long count = influxdbService.countResultProcess(countQuery);
        // 封装分页数据
        PageBean pageBean = new PageBean(select.getPageNum(), select.getPageSize(), Integer.parseInt(String.valueOf(count)));
        QueryResult query = influxdbService.query(String.format(InfluxSql.limitSql, selectParam, pageBean.getPageSize(), pageBean.getStart()));
        List<Map<String, Object>> maps = influxdbService.queryResultProcess(query);
        pageBean.setList(maps);
        pageBean.setTotalRecord(Integer.parseInt(String.valueOf(count)));
        // 转本系统分页数据
        IPage<WmcsWarnVo> warnVoIPage = new Page<>();
        warnVoIPage.setCurrent(pageBean.getPageNum());
        warnVoIPage.setPages(pageBean.getTotalPage());
        warnVoIPage.setSize(pageBean.getPageSize());
        warnVoIPage.setTotal(pageBean.getTotalRecord());
        List<WmcsWarnVo> list = JSONArray.parseArray(JSON.toJSONString(pageBean.getList()), WmcsWarnVo.class);
        List<String> channelIds = list.stream().map(WmcsWarnVo::getChannelId).collect(Collectors.toList());
        if (StringUtils.isNotEmpty(channelIds) && StringUtils.isNotBlank(channelIds.get(0))) {
            List<ChannelVo> channelVoList = deviceChannelService.listByChannelIdIds(channelIds);
            list.forEach(l -> {
                List<ChannelVo> tempList = channelVoList.stream().filter(i -> i.getChannelId().equals(l.getChannelId())).collect(Collectors.toList());
                l.setChannelName(StringUtils.isNotEmpty(tempList) ? tempList.get(0).getChannelName() : "");
                l.setTreeName(StringUtils.isNotEmpty(tempList) ? tempList.get(0).getTreeName() : "");
            });
        }
        warnVoIPage.setRecords(list);
        return JsonResult.ok(warnVoIPage);
    }

    @PostMapping("/add")
    public JsonResult add (@RequestBody WmcsWarnVo wmcsWarn) {
        String measurement = "wmcs";
        Map<String, String> tags = new HashMap<>();
        tags.put("type", wmcsWarn.getType());
        tags.put("channelId", wmcsWarn.getChannelId());
        Map<String, java.lang.Object> fields = new HashMap<>();
        fields.put("images", wmcsWarn.getImages());
        fields.put("content", wmcsWarn.getContent());
        fields.put("createTime", wmcsWarn.getCreateTime());
        fields.put("timestamp", wmcsWarn.getTimestamp());
        influxdbService.insert(measurement, tags, fields, System.currentTimeMillis());
        return JsonResult.ok();
    }

    @GetMapping("/groupList")
    public JsonResult groupList() {
        List<WarnCountVo> resultList = new ArrayList<>();
        List<String> channelIds = new ArrayList<>();
        // 查询数据总数
        String currentMonthFirth = cn.wxgis.jc.common.util.DateUtils.getBeginDayByMonth(DateUtils.getCurrentMonth()) + "T00:00:00Z";
        String currentMonthEnd = DateUtils.getEndDayByMonth(DateUtils.getCurrentMonth()) + "T23:59:59Z";
        String sql = "SELECT count(content) FROM \"wmcs\" where time >= '%s' and time <= '%s' group by type, channelId";
        QueryResult query = influxdbService.query(String.format(sql, currentMonthFirth, currentMonthEnd));
        if (StringUtils.isEmpty(query.getResults())) return JsonResult.ok(resultList);
        List<Map<String, String>> list = new ArrayList<>();
        for(QueryResult.Result r : query.getResults()){
            List<QueryResult.Series> seriesList = r.getSeries();
            if(seriesList != null && seriesList.size() != 0) {
                for(QueryResult.Series series : seriesList){
                    Map<String, String> tagMap = series.getTags();
                    List<String> columns = series.getColumns();
                    String[] keys = columns.toArray(new String[columns.size()]);
                    List<List<Object>> values = series.getValues();
                    if(values != null && values.size() != 0) {
                        for(List<Object> value : values){
                            for (int i = 0; i < keys.length; i++) {
                                tagMap.put(keys[i], String.valueOf(value.get(i)));
                            }
                        }
                    }
                    channelIds.add(String.valueOf(tagMap.get("channelId")));
                    list.add(tagMap);
                }
            }
        }
        if (StringUtils.isNotEmpty(list)) {
            // 查询类型列表
            List<SysDictData> dictDataList = dictDataService.listByDictType("work_alarm_type");
            Map<String, String> dictDataMap = new HashMap<>();
            if (StringUtils.isNotEmpty(dictDataList)) {
                dictDataMap = dictDataList.stream().collect(Collectors.toMap(SysDictData::getDictValue, SysDictData::getDictLabel));
            }
            // 查询设备列表
            List<ChannelVo> channelList = deviceChannelService.listByChannelIdIds(channelIds);
            Map<String, Object> channelMap = new HashMap<>();
            if (StringUtils.isNotEmpty(channelList)) {
                channelMap = channelList.stream().collect(Collectors.toMap(ChannelVo::getChannelId, channel -> channel));
            }

            for (Map<String, String> map : list) {
                WarnCountVo warnCount = new WarnCountVo();
                warnCount.setType(map.get("type"));
                warnCount.setTypeName((StringUtils.isNotEmpty(dictDataMap) && StringUtils.isNotNull(dictDataMap.get(warnCount.getType()))) ? dictDataMap.get(warnCount.getType()) : "未知");
                warnCount.setCount(new BigDecimal(map.get("count")));
                String time = map.get("time");
                warnCount.setMonth(time.substring(0, time.indexOf("-") + 3));
                warnCount.setChannelId(map.get("channelId"));
                if (StringUtils.isNotEmpty(channelMap) && StringUtils.isNotNull(channelMap.get(warnCount.getChannelId()))) {
                    ChannelVo channel = (ChannelVo) channelMap.get(warnCount.getChannelId());
                    warnCount.setChannelName(channel.getChannelName());
                    warnCount.setPointId(channel.getPointId());
                    warnCount.setPointName(channel.getPointName());
                    warnCount.setPointId(channel.getPointId());
                    warnCount.setPointTypeId(channel.getPointTypeId());
                    warnCount.setPointTypeName(channel.getPointTypeName());
                    warnCount.setRegionName(channel.getRegionName());
                    warnCount.setRegionCode(channel.getRegionCode());
                }
                resultList.add(warnCount);
            }

        }
        return JsonResult.ok(resultList);
    }

    public static void main(String[] args) {
        String time = "2023-11-01T00:00:00Z";
        System.out.println(time.substring(0, time.indexOf("-") + 3));
    }

}
