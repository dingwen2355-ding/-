package com.znv.manage.common.bean.plan;


import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 预案数字化基本信息bean
 *
 * @author chd
 * @date 2022/5/9
 */
@Data
public class PlanNumInfoBean {
    String id;

    String name;

    String emergencyTypeId;

    String emergencyTypeName;

    String createId;

    String createName;

    String createTime;

    String modifyTime;

    String levelId;

    String levelName;

    List<String> fileUrls;

    List<Map<String, Object>> fileData;

    List<PlanNumDataBean> planNumDataBeans;
}
