package com.znv.manage.dao.resource;

import com.znv.manage.common.bean.resource.BasicExpertInfo;
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
public interface BasicExpertDao {

    List<Map<String, Object>> getBasicExpertList(@Param("id") Integer id,
                                                 @Param("name") String name,
                                                 @Param("sex") Integer sex,
                                                 @Param("type") String type,
                                                 @Param("title") String title,
                                                 @Param("company") String company,
                                                 @Param("expertCategory") String expertCategory,
                                                 @Param("address") String address,
                                                 @Param("planId") String planId,
                                                 @Param("eventId") String eventId,
                                                 @Param("pageStart") Integer pageStart,
                                                 @Param("pageLen") Integer pageLen);

    Map<String, Object> getBasicExpertListById(@Param("id") String id);



    Integer getBasicExpertListCount(@Param("id") Integer id,
                                    @Param("name") String name,
                                    @Param("sex") Integer sex,
                                    @Param("type") String type,
                                    @Param("title") String title,
                                    @Param("company") String company,
                                    @Param("address") String address,
                                    @Param("expertCategory") String expertCategory);

    int insertBasicExpertInfo(BasicExpertInfo basicExpertInfo);

    int insertBasicExpertInfoList(@Param("list") List<BasicExpertInfo> resultList);

    int updateBasicExpertInfo(BasicExpertInfo basicExpertInfo);

    int deleteBasicExpertInfo(@Param("ids") String ids);

    List<BasicExpertInfo> getBasicExpertType(@Param("name") String name);

    List<Map<String, Object>> getBasicExpertTitle();

    int queryExist(BasicExpertInfo basicExpertInfo);
}
