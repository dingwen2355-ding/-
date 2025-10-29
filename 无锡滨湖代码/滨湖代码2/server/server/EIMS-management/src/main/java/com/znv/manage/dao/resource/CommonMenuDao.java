package com.znv.manage.dao.resource;

import com.alibaba.fastjson.JSONObject;
import com.znv.manage.bean.BasicCompanyInfo;
import com.znv.manage.bean.StockInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author znv
 * @ClassName:
 * @Description: xx描述xx
 * @date
 */
@Repository
public interface CommonMenuDao {

    List<Map<String, Object>> getRegion();

    List<Map<String, Object>> getBelongUnit(@Param("area") String area);

    List<Map<String, Object>> getBelongArea();

    List<Map<String, Object>> getBeManagedType();

    List<Map<String, Object>> getStorageLevel();

    List<Map<String, Object>> getStorageType();

    List<Map<String, Object>> getIndustry();

    List<Map<String, Object>> getHelpType();

    String queryStockByRegion(@Param("region") String region);
}
