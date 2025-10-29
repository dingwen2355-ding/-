package com.znv.manage.dao;

import com.alibaba.fastjson.JSONArray;
import com.znv.manage.common.bean.Individual;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface IndividualDao {
    void saveIndividual(@Param("lists") List<Individual> lists);

    void saveIndividualGps(@Param("lists") List<Map<String, String>> lists);

    List<String> queryIndividualUid(@Param("type") String type);

    List<Map<String,Object>> queryIndividualIds();

    int updateIndividualGps(@Param("list")JSONArray jsonArray);

}
