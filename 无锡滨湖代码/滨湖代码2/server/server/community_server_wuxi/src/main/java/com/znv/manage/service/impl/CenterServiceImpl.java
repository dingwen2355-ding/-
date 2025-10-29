package com.znv.manage.service.impl;

import com.znv.manage.center.dao.CenterDao;
import com.znv.manage.common.bean.Result;
import com.znv.manage.common.utils.DateUtil;
import com.znv.manage.common.exception.ResultCodeEnum;
import com.znv.manage.service.CenterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Slf4j
public class CenterServiceImpl implements CenterService {
    @Autowired
    private CenterDao centerDao;

    @Override
    public List<Map<String, Object>> queryCoalMine() {
        return centerDao.queryCoalMine();
    }

    @Override
    public List<Map<String, Object>> queryEnterprise() {
        return centerDao.queryEnterprise();
    }

    @Override
    public List<Map<String, Object>> queryObservationStation() {
        return centerDao.queryObservationStation();
    }

    @Override
    public List<Map<String, Object>> queryProneDisaster() {
        return centerDao.queryProneDisaster();
    }

    @Override
    public List<Map<String, Object>> queryWaterlogging() {
        return centerDao.queryWaterlogging();
    }

    @Override
    public List<Map<String, Object>> queryReservoir() {
        return centerDao.queryReservoir();
    }

    @Override
    public List<Map<String, Object>> queryTransport() {
        return centerDao.queryTransport();
    }

    @Override
    public List<Map<String, Object>> queryWeatherStation() {
        return centerDao.queryWeatherStation();
    }

    @Override
    public List<Map<String, Object>> queryRescue() {
        return centerDao.queryRescue();
    }

    @Override
    public List<Map<String, Object>> queryHazard() {
        return centerDao.queryHazard();
    }

    @Override
    public List<Map<String, Object>> queryMedicalInstitution() {
        return centerDao.queryMedicalInstitution();
    }

    @Override
    public Long queryCount() {
        Date date = DateUtil.getYearFirst(2023);
        String nowTime =DateUtil.dateToStrLong(date);
        long sum = 1173571; // 2023年以前的总量
        List<String> list = centerDao.queryTableNameList(Timestamp.valueOf(nowTime)); // 获取2023年1月1日后的数据表
        sum = sum + centerDao.queryCount(list);
        return sum;
    }

    @Override
    public Result queryMonthCount() {
        Result result = new Result();
        try {
            String yearMonth = getNowMonth();
            Map<String,Object> map = new HashMap<>();
            map.put("monthCount",centerDao.queryMonthCount(yearMonth));
            map.put("todayCount",centerDao.queryDayCount(yearMonth));
            result.setData(map);
        } catch (BadSqlGrammarException e) {
            Map<String,Object> map = new HashMap<>();
            map.put("monthCount","");
            map.put("todayCount","");
            result.setData(map);
            log.error(e.getMessage());
        }
        return result;
    }

    @Override
    public Result queryWeibo() {
        Result result = new Result();
        try {
            String nowMonth = getNowMonth();
            String lastMonth = getLastMonth();
            String tableName = centerDao.queryTableName();
            if(StringUtils.isEmpty(tableName)){  // 如果本月的表不存在，则查上个月的数据
                result.setData(centerDao.queryWeibo(lastMonth,lastMonth));
            }else {
                result.setData(centerDao.queryWeibo(nowMonth,lastMonth));
            }
        } catch (Exception e) {
            result.setCode(1);
            result.setMessage(e.getMessage());
            log.error(e.getMessage());
        }
        return result;
    }

    @Override
    public Result queryOthers() {
        Result result = new Result();
        try {
            String nowMonth = getNowMonth();
            String lastMonth = getLastMonth();
            String tableName = centerDao.queryTableName();
            if(StringUtils.isEmpty(tableName)){ // 如果本月的表不存在，则查上个月的数据
                result.setData(centerDao.queryOthers(lastMonth,lastMonth));
            }else {
                result.setData(centerDao.queryOthers(nowMonth,lastMonth));
            }
        } catch (Exception e) {
            result.setCode(1);
            result.setMessage(e.getMessage());
            log.error(e.getMessage());
        }
        return result;
    }

    @Override
    public Result query48Hours() {
        Result result = new Result();
        try {
            String nowMonth = getNowMonth();
            String lastMonth = getLastMonth();
            result.setData(centerDao.query48Hours(nowMonth,lastMonth));
        } catch (BadSqlGrammarException e) {
            log.error(e.getMessage());
        }
        return result;
    }

    private List<String> getYearMonth() {
        List<String> list = new ArrayList();
        Calendar calendar = Calendar.getInstance();
        // 获取当前年
        int year = calendar.get(Calendar.YEAR);
        // 获取当前月
        int month = calendar.get(Calendar.MONTH) + 1;
        String s = "";
        int size = (year - 2020) * 12 + month - 12;
        DecimalFormat df=new DecimalFormat("00");
        for (int i = 0; i <= size; i++) {
            s = year + "_" + df.format(month);
            month--;
            if (month == 0) {
                year--;
                month = 12;
            }
            list.add(s);
        }
        return list;
    }


    private String getLastMonth() {
        LocalDate today = LocalDate.now();
        today = today.minusMonths(1);
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("yyyy_MM");
        return formatters.format(today);
    }

    private String getNowMonth(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy_MM");
        Date date = new Date();
        String month = format.format(date);
        return month;
    }

    @Override
    public Result queryWeather() {
        Result result = new Result();
        try {
            result.setData(centerDao.queryLastWeather());
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(e.getMessage());
            result.setData(e.toString());
        }
        return result;
    }
}
