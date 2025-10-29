package com.znv.manage.dao.resource;

import com.znv.manage.common.bean.resource.Risk;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface RiskDao {
    List<Risk> queryRiskList(@Param("industry") String industry,
                             @Param("enterpriseName") String enterpriseName,
                             @Param("rectificationProgress") String rectificationProgress,
                             @Param("startTime") String startTime,
                             @Param("endTime") String endTime);

    int deleteByPrimaryKey(String ids);

    int insertSelective(Risk risk);

    int updateByPrimaryKeySelective(Risk risk);

    int insertSelectiveList(List<Risk> riskList);

}
