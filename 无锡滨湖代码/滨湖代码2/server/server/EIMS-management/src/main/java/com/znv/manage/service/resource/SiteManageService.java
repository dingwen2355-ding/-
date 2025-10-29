package com.znv.manage.service.resource;

import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.resource.SiteManageBean;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author yangbo
 * @email
 * @date 2022/11/9
 */
public interface SiteManageService {

    Result queryForList(String name, String category, String address, String acreage, String precinctTreeId, String precinctId, String MAXGALLERYFUL, String streetName, String community, String type, Integer pageNum, Integer pageSize);

    Result save(SiteManageBean siteManageBean);

    Result remove(String id);

    Result exportSite(String name, String category, String address, String acreage, String precinctTreeId, String precinctId, String MAXGALLERYFUL, String streetName, String community, String type, String ids);

    Result importSite(MultipartFile file);

    Result queryForResource(String shelterOrga, String shelterName, String shelterCategory, String planId, Integer pageStart, Integer pageLen);

    Result selectBasicShelterList(String shelterOrga, String shelterName, String shelterCategory, String eventId, String isAll, Integer pageStart, Integer pageLen);

}
