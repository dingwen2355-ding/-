package com.znv.manage.service.resource;

import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.resource.BasicExpertInfo;

import java.util.List;
import java.util.Map;

/**
 * @author znv
 * @ClassName: ManageService
 * @Description:
 * @date 2018/5/18 14:56
 */
public interface BasicExpertService {

    Result getBasicExpertList(Integer id, String name, Integer sex, String type,
                              String title, String company, String expertCategory, String address,
                              String planId, String eventId, String isAll, Integer start, Integer pageLen);


    Result insertBasicExpertInfo(BasicExpertInfo basicExpertInfo);

    Result insertBasicExpertInfoList(List<BasicExpertInfo> basicExpertInfo);

    Result updateBasicExpertInfo(BasicExpertInfo basicExpertInfo);

    Result deleteBasicExpertInfo(String ids);

    List<BasicExpertInfo> getBasicExpertType(String name);

    List<Map<String, Object>> getBasicExpertTitle();

    boolean queryExist(BasicExpertInfo basicExpertInfo);
}
