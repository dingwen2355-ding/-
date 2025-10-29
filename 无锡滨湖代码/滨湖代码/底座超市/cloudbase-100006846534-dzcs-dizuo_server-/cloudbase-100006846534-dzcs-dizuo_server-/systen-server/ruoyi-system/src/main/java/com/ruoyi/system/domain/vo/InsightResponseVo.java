package com.ruoyi.system.domain.vo;

import com.alibaba.fastjson2.JSONArray;
import lombok.Data;

public class InsightResponseVo extends JSONArray {

    @Data
    public static class InsightResponseData {
        Integer value;
        String name;


        public InsightResponseData(String name, Integer value) {
            this.name = name;
            this.value = value;
        }
    }

    @Data
    public static class InsightResponseDatas {
        String name;
        String value1;
        String value2;
        String value3;
        String value4;

        public InsightResponseDatas(String name, String value1, String value2, String value3, String value4) {
            this.name = name;
            this.value1 = value1;
            this.value2 = value2;
            this.value3 = value3;
            this.value4 = value4;
        }
    }
}
