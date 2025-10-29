package com.znv.manage.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import net.sf.json.xml.XMLSerializer;

public class XmlUtils {
    public static JSONObject xmlToJson(String xml) {
        XMLSerializer xmlSerializer = new XMLSerializer();
        String result = xmlSerializer.read(xml).toString();
        JSONObject jsonObject = JSON.parseObject(result);
        return jsonObject;
    }
}
