package com.znv.manage.dao;

import com.znv.manage.common.bean.AIsxt;
import com.znv.manage.common.bean.AlarmStatisticsResultBean;
import com.znv.manage.common.bean.SpAIgjxx;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public interface SpAIgjxxDao {
    int insertSpAIgjxxList(@Param("list") List<SpAIgjxx> resultList);

    int insertAIsxtList(@Param("list") List<AIsxt> resultList);

    List<HashMap<String,Object>> querySpAIgjxxAlarmIdList();

    List<HashMap<String,Object>> queryAIsxtIdList();

    int countSpAIgjxxToday(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

    List<Map<String, String>> SpAIyjTodayList(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

    List<Map<String, String>> SpAIgjxxTodayList(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

    List<AlarmStatisticsResultBean> SpAIgjxxTop5Today(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime);
}
