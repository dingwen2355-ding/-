package com.znv.manage.common.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class JsonUtils {

    public static JSONArray getJSONArray(JSONObject jsonObject, String key){
        if(jsonObject!=null){
            if(jsonObject.containsKey(key)){
                return jsonObject.getJSONArray(key);
            }else{
                return null;
            }
        }else{
            return null;
        }
    }

    public static JSONObject getJSONObject(JSONObject jsonObject,String key){
        if(jsonObject!=null){
            if(jsonObject.containsKey(key)){
                return jsonObject.getJSONObject(key);
            }else{
                return null;
            }
        }else{
            return null;
        }
    }

    public static String getJSONString(JSONObject jsonObject,String key){
        if(jsonObject!=null){
            if(jsonObject.containsKey(key)){
                return jsonObject.getString(key);
            }else{
                return "";
            }
        }else{
            return "";
        }
    }
}
