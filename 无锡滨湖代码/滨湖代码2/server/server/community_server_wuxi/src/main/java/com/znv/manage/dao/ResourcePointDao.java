package com.znv.manage.dao;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 地图撒点
 *
 * @author 0049003788
 */
@Repository
public interface ResourcePointDao {
    List<Map<String, Object>> selectSchool();

    List<Map<String, Object>> selectExpert();

    List<Map<String, Object>> selectShelter();

    List<Map<String, Object>> selectStock();
}
