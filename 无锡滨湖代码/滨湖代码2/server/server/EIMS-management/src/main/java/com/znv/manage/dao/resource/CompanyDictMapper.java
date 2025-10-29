package com.znv.manage.dao.resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CompanyDictMapper {

    List<Map<String, Object>> queryRegion();

    List<Map<String, Object>> queryCompanyTypeParent();

    List<Map<String, Object>> queryCompanyType(@Param(value = "parentId") String parentId);

    List<Map<String, Object>> queryStandLevel();

    List<Map<String, Object>> queryCompanyState();

    List<Map<String, Object>> queryIndustry();
}