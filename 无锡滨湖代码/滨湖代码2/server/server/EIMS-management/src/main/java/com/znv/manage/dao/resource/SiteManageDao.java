package com.znv.manage.dao.resource;

import com.znv.manage.common.bean.resource.SiteManageBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author yangbo
 * @email
 * @date 2022/11/9
 */
@Repository
public interface SiteManageDao {

    List<SiteManageBean> queryForList(@Param("name") String name,
                                      @Param("category") String category,
                                      @Param("address") String address,
                                      @Param("acreage") String acreage,
                                      @Param("precinctTreeId") String precinctTreeId,
                                      @Param("precinctId") String precinctId,
                                      @Param("MAXGALLERYFUL") String MAXGALLERYFUL,
                                      @Param("streetName") String streetName,
                                      @Param("community") String community,
                                      @Param("type") String type,
                                      @Param("ids") String ids
    );

    int insert(SiteManageBean siteManageBean);

    int update(SiteManageBean siteManageBean);

    int remove(@Param("ids") List<String> ids);

    List<String> getSiteId();

    int getCurrentPerson(@Param("sitId") String sitId);

    void updateCurrentPerson(@Param("count") int count,
                             @Param("sitId") String sitId);

    List<Map<String, Object>> queryForResource(@Param("shelterOrga") String shelterOrga,
                                               @Param("shelterName") String shelterName,
                                               @Param("shelterCategory") String shelterCategory,
                                               @Param("planId") String planId,
                                               @Param("pageStart") Integer pageStart,
                                               @Param("pageLen") Integer pageLen);

    public List<Map<String,Object>> selectBasicShelterEntityList(@Param("shelterOrga") String shelterOrga,
                                                                 @Param("shelterName") String shelterName,
                                                                 @Param("shelterCategory") String shelterCategory,
                                                                 @Param("eventId") String eventId,
                                                                 @Param("pageStart") Integer pageStart,
                                                                 @Param("pageLen") Integer pageLen);
}
