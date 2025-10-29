package com.grandlynn.util;

import com.alibaba.fastjson.JSON;
import java.util.Map;
import java.util.TreeMap;

public class JsonUtil {
   public static <T> T map2Bean(Map<String, ?> mapData, T t) {
      return (T) JSON.parseObject(JSON.toJSONString(mapData), t.getClass());
   }

   public static <T> Map<String, String> bean2Map(T t) {
      return (Map)JSON.parseObject(JSON.toJSONString(t), TreeMap.class);
   }

   public static <T> String bean2JsonString(T t) {
      return JSON.toJSONString(t);
   }

   public static <T> T json2Bean(String jsonData, Class<T> clazz) {
      return JSON.parseObject(jsonData, clazz);
   }

   public static Map<String, String> json2Map(String jsonData) {
      return (Map)JSON.parseObject(jsonData, TreeMap.class);
   }

   public static <T> T map2Bean(Map<String, ?> mapData, Class<T> clazz) {
      return JSON.parseObject(JSON.toJSONString(mapData), clazz);
   }
}
