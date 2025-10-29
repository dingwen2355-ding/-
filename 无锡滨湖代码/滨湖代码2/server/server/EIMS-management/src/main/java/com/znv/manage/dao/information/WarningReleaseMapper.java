package com.znv.manage.dao.information;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.znv.manage.common.bean.information.WarningRelease;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Repository
public interface WarningReleaseMapper {
    int deleteByPrimaryKey(Long id);

    int insert(WarningRelease record);

    int updateByPrimaryKey(WarningRelease record);

    List<WarningRelease> getWarning(@Param(value = "id") Long id,
                                    @Param(value = "title") String title,
                                    @Param(value = "deal") String deal,
                                    @Param(value = "type1") String type1,
                                    @Param(value = "type2") String type2,
                                    @Param(value = "level") String level,
                                    @Param(value = "startDate") String startDate,
                                    @Param(value = "endDate") String endDate,
                                    @Param(value = "area") String area,
                                    @Param(value = "flag") String flag);

    List<WarningRelease> getWarningScreen(@Param(value = "id") Long id,
                                          @Param(value = "title") String title,
                                          @Param(value = "deal") String deal,
                                          @Param(value = "type1") String type1,
                                          @Param(value = "type2") String type2,
                                          @Param(value = "level") String level,
                                          @Param(value = "startDate") String startDate,
                                          @Param(value = "endDate") String endDate,
                                          @Param(value = "area") String area);

    List<WarningRelease> getWarnings(@Param(value = "list") List<String> ids);

    int insertFireData(@Param("datas") JSONArray array);

    int queryExist(@Param("id") String id);

    int queryRiverExist(@Param("id") String id);

    int updateRiverData(@Param("map") Map<String, String> map);

    int insertRiverData(@Param("datas") List<Map<String, String>> list);

    int dealWarning(Long id, String content, String deal);

    Map<String,String> warningCount(@Param("area") String area, @Param("type") String type,
                                    @Param("startDate") String startDate,
                                    @Param("endDate") String endDate);

    @MapKey("keyid")
    Map<String, Map<String, Object>> selectTodaySevereWeather();

    Set<Date> selectTodaySevereWeatherReleaseTimeByKeyId(@Param("keyId") String keyId);

    Set<String> selectTodaySevereWeatherTitleByKeyId(@Param("keyId") String keyId);

    int batchInsertSevereWeatherWarning(@Param("list") List<JSONObject> list);

    int updateSevereWeatherWarningById(@Param("item") JSONObject jsonObject);

}