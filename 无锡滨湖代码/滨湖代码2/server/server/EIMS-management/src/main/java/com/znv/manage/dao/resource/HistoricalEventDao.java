package com.znv.manage.dao.resource;

import com.znv.manage.common.bean.resource.HistoricalEvent;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoricalEventDao {
    int deleteHistoricalEvent(String ids);

    int insertSelective(HistoricalEvent record);

    int updateByPrimaryKeySelective(HistoricalEvent record);

    List<HistoricalEvent> queryHistoricalEventList(@Param("accidentTitle") String accidentTitle,
                                                   @Param("accidentAddress") String accidentAddress,
                                                   @Param("accidentType") String accidentType);

    String queryAnnexUrl(@Param("id") Long id);
}
