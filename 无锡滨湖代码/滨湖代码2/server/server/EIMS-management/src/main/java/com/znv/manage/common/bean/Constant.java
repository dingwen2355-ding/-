package com.znv.manage.common.bean;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class Constant {


    public static final Map<String, String> ROLE_ID = new HashMap<String, String>();
    static {
        ROLE_ID.put("超管", "1");
        ROLE_ID.put("街道坐席人员", "10");
        ROLE_ID.put("街道管理员", "20");
        ROLE_ID.put("街道科室", "30");
        ROLE_ID.put("社区人员", "40");
        ROLE_ID.put("网格员", "50");
    }

    public static final Map<String, String> EVENTSTATE_ID = new HashMap<String, String>();
    static {
        EVENTSTATE_ID.put("报废", "10");
        EVENTSTATE_ID.put("已反馈", "20");
        EVENTSTATE_ID.put("待派遣", "30");
        EVENTSTATE_ID.put("退回待派遣", "35");
        EVENTSTATE_ID.put("待接收", "40");
        EVENTSTATE_ID.put("处置中", "50");
        EVENTSTATE_ID.put("已处置", "60");
    }

    public static final Map<String, String> NODE_ID = new HashMap<String, String>();
    static {
        NODE_ID.put("数据接入", "1");
        NODE_ID.put("提交区级", "9");
        NODE_ID.put("非我所属", "10");
        NODE_ID.put("已派遣", "20");
        NODE_ID.put("抢单", "30");
        NODE_ID.put("追回", "40");
        NODE_ID.put("撤销", "45");
        NODE_ID.put("接收", "50");
        NODE_ID.put("已处置", "60");
        NODE_ID.put("报废", "70");
    }

    public static final Map<String, String> OVERDUE_ID = new HashMap<String, String>();
    static {
        OVERDUE_ID.put("正常", "10");
        OVERDUE_ID.put("即将超期", "20");
        OVERDUE_ID.put("已超期", "30");
    }

    public static final Map<String, String> TAG_ID = new HashMap<String, String>();
    static {
        TAG_ID.put("产业经济", "1");
        TAG_ID.put("城市管理", "2");
        TAG_ID.put("民生服务", "3");
        TAG_ID.put("平安建设", "4");
        TAG_ID.put("应急管理", "5");
        TAG_ID.put("综合执法", "6");
    }

}
