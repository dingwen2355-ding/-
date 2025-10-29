package cn.wxgis.jc.common.web.controller;


import cn.wxgis.jc.common.constant.Constant;
import cn.wxgis.jc.common.util.StringUtils;

/**
 * web层通用数据处理
 */
public class BaseController {

    public static boolean isAdmin(String userId)
    {
        return StringUtils.isNotBlank(userId) && Constant.DEFAULT_USER_ID.equals(userId);
    }

}
