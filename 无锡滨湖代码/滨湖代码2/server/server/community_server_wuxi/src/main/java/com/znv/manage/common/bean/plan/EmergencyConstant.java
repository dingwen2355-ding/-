package com.znv.manage.common.bean.plan;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 6x7
 * @date 2022/05/09 14:55
 * @description
 */
public class EmergencyConstant {
    private static final String[] EVENT_LEVEL_ARRAY = {"特别重大", "重大", "较大", "一般", "其他"};

    public static String[] getEVENT_LEVEL_ARRAY() {
        return EVENT_LEVEL_ARRAY;
    }

    /**
     * 前期主备
     */
    private static final String[] PLAN_NUM_PRE_ARRAY = {"指挥体系", "工作组", "部门职责", "关联队伍",
            "关联安置场所", "关联物资", "专家", "关联系统"};

    /**
     * 应急响应
     */
    private static final String[] EMERGENCY_RESPONSE_ARRAY = {"应急响应"};

    /**
     * 应急响应保障
     */
    private static final String[] EMERGENCY_SUPPORT_ARRAY = {"通信保障", "应急队伍保障", "应急物资保障", "人员转移保障",
            "供电保障", "能源保障", "交通运输保障", "医疗保障", "治安保障", "资金保障"};

    /**
     * 应急响应终止
     */
    private static final String[] EMERGENCY_STOP_ARRAY = {"应急响应终止"};

    /**
     * 奖励与责任追究机制
     */
    private static final String[] EMERGENCY_REWARD_DUTY_ARRAY = {"奖励与责任追究"};

    private static final List<LabelBean> EMERGENCY_RESPONSE_TYPE = new ArrayList();

    public static List<LabelBean> getEmergencyResponseType() {
        return EMERGENCY_RESPONSE_TYPE;
    }

    private static final JSONArray MONITOR_TYPE = new JSONArray();

    public static JSONArray getMonitorType() {
        return MONITOR_TYPE;
    }

    private static final Map<String, String> MONITOR_TYPE_MAP = new HashMap();

    public static Map<String, String> getMonitorTypeMap() {
        return MONITOR_TYPE_MAP;
    }

    private static final Map<String, String> EMERGENCY_RESPONSE_TYPE_MAP = new HashMap();

    public static Map<String, String> getEmergencyResponseTypeMap() {
        return EMERGENCY_RESPONSE_TYPE_MAP;
    }

    private static final Map<String, String> COLUMN_NAMES = new HashMap();

    private static final JSONArray MSG_TYPE_TREE = new JSONArray();

    public static JSONArray getMsgTypeTree() {
        return MSG_TYPE_TREE;
    }

    static {
        JSONObject jsonObject1 = buildJSONObject("1", "自然灾害");
        List<JSONObject> jsonObject1List = new ArrayList();
        jsonObject1List.add(buildJSONObject("10", "防汛抗旱"));
        jsonObject1List.add(buildJSONObject("11", "森林防火"));
        jsonObject1List.add(buildJSONObject("12", "抗震救灾"));
        jsonObject1List.add(buildJSONObject("13", "其它"));
        jsonObject1.put("children", jsonObject1List);
        MSG_TYPE_TREE.add(jsonObject1);
        JSONObject jsonObject2 = buildJSONObject("2", "事故灾难");
        List<JSONObject> jsonObject2List = new ArrayList();
        jsonObject2List.add(buildJSONObject("20", "矿山救援"));
        jsonObject2List.add(buildJSONObject("21", "危化品救援"));
        jsonObject2List.add(buildJSONObject("22", "消防"));
        jsonObject2List.add(buildJSONObject("23", "其它"));
        jsonObject2.put("children", jsonObject2List);
        MSG_TYPE_TREE.add(jsonObject2);
        MSG_TYPE_TREE.add(buildJSONObject("3", "气象预警"));
    }

    static {
        COLUMN_NAMES.put("应急响应", "yingji_xiangying");
        COLUMN_NAMES.put("应急响应终止", "xiangying_end");

        COLUMN_NAMES.put("通信保障", "tongxing");
        COLUMN_NAMES.put("应急队伍保障", "yingji_duiwu");
        COLUMN_NAMES.put("应急物资保障", "yingji_wuzi");
        COLUMN_NAMES.put("人员转移保障", "renyuan_zhuanyi");
        COLUMN_NAMES.put("供电保障", "gongdian");
        COLUMN_NAMES.put("能源保障", "nengyuan");
        COLUMN_NAMES.put("交通运输保障", "jiaotong");
        COLUMN_NAMES.put("医疗保障", "yiliao");
        COLUMN_NAMES.put("治安保障", "zhian");
        COLUMN_NAMES.put("资金保障", "zijin");

        COLUMN_NAMES.put("指挥体系", "zhihui");
        COLUMN_NAMES.put("工作组", "groups");
        COLUMN_NAMES.put("部门职责", "bumen");
        COLUMN_NAMES.put("关联队伍", "guanlian_duiwu");
        COLUMN_NAMES.put("关联安置场所", "guanlian_anzhi");
        COLUMN_NAMES.put("关联物资", "guanlian_wuzi");
        COLUMN_NAMES.put("专家", "zhuanjia");
        COLUMN_NAMES.put("关联系统", "guanlian_xitong");
        COLUMN_NAMES.put("奖励与责任追究", "jiangli_zeren");
    }

    static {
        EMERGENCY_RESPONSE_TYPE.add(LabelBean.buildLabelBean("1", "特别重大"));
        EMERGENCY_RESPONSE_TYPE.add(LabelBean.buildLabelBean("2", "重大"));
        EMERGENCY_RESPONSE_TYPE.add(LabelBean.buildLabelBean("3", "较大"));
        EMERGENCY_RESPONSE_TYPE.add(LabelBean.buildLabelBean("4", "一般"));
    }

    static {
        EMERGENCY_RESPONSE_TYPE_MAP.put("1", "特别重大");
        EMERGENCY_RESPONSE_TYPE_MAP.put("2", "重大");
        EMERGENCY_RESPONSE_TYPE_MAP.put("3", "较大");
        EMERGENCY_RESPONSE_TYPE_MAP.put("4", "一般");
    }

    static {
        MONITOR_TYPE_MAP.put("1", "气象水文信息");
        MONITOR_TYPE_MAP.put("2", "工程信息");
        MONITOR_TYPE_MAP.put("3", "洪涝灾害信息");
        MONITOR_TYPE_MAP.put("5", "旱情信息");
        MONITOR_TYPE_MAP.put("6", "洪水预警");
        MONITOR_TYPE_MAP.put("7", "山洪灾害预警");
        MONITOR_TYPE_MAP.put("8", "台风灾害预警");
        MONITOR_TYPE_MAP.put("9", "干旱预警");
        MONITOR_TYPE_MAP.put("10", "市防预警");
        MONITOR_TYPE_MAP.put("11", "监测");
        MONITOR_TYPE_MAP.put("12", "预警");
    }

    static {
        List<LabelBean> labelBeans1 = new ArrayList();
        List<LabelBean> labelBeans2 = new ArrayList();
        labelBeans1.add(LabelBean.buildLabelBean("1", "气象水文信息"));
        labelBeans1.add(LabelBean.buildLabelBean("2", "工程信息"));
        labelBeans1.add(LabelBean.buildLabelBean("3", "洪涝灾害信息"));
        labelBeans1.add(LabelBean.buildLabelBean("5", "旱情信息"));
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("value", "11");
        jsonObject.put("label", "监测");
        jsonObject.put("children", labelBeans1);
        MONITOR_TYPE.add(jsonObject);
        labelBeans2.add(LabelBean.buildLabelBean("6", "洪水预警"));
        labelBeans2.add(LabelBean.buildLabelBean("7", "山洪灾害预警"));
        labelBeans2.add(LabelBean.buildLabelBean("8", "台风灾害预警"));
        labelBeans2.add(LabelBean.buildLabelBean("9", "干旱预警"));
        labelBeans2.add(LabelBean.buildLabelBean("10", "市防预警"));
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("value", "12");
        jsonObject1.put("label", "预警");
        jsonObject1.put("children", labelBeans2);
        MONITOR_TYPE.add(jsonObject1);
    }

    private static final Map<Integer, String> EVENT_HANDLE_DICT;

    static {
        EVENT_HANDLE_DICT = new HashMap<>(16);
        EVENT_HANDLE_DICT.put(1, "签收");
        EVENT_HANDLE_DICT.put(2, "核销");
        EVENT_HANDLE_DICT.put(3, "结案");
        EVENT_HANDLE_DICT.put(4, "退回");
    }

    public static Map<Integer, String> getEVENT_HANDLE_DICT() {
        return EVENT_HANDLE_DICT;
    }

    public static JSONObject buildJSONObject(String value, String label) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("label", label);
        jsonObject.put("value", value);
        return jsonObject;
    }

}
