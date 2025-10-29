package com.znv.manage.common.bean;

import java.util.HashMap;
import java.util.Map;

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

    public static final Map<String,String> PRECINCT_KIND =new HashMap<String,String>();
    static {
        PRECINCT_KIND.put("社区","110");
        PRECINCT_KIND.put("网格","120");
        PRECINCT_KIND.put("科室","130");
    }

    public static final Map<String,String> USER_STATE = new HashMap<String,String>();
    static {
        USER_STATE.put("正常","0");
        USER_STATE.put("已锁定","1");
    }

}
