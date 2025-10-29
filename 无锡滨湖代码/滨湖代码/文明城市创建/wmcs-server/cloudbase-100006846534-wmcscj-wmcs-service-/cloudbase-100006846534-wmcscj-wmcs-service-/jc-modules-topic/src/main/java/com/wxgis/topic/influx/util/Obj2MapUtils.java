package com.wxgis.topic.influx.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

public class Obj2MapUtils {


    public static Map<String, String> jsonToStringMap(String json) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Map<String, String> m = mapper.readValue(json, Map.class);
            return m;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
