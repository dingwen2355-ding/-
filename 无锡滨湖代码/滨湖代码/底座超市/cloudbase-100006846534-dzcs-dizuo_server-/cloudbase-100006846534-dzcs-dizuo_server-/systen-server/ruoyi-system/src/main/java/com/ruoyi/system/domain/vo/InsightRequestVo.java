package com.ruoyi.system.domain.vo;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
/**
 * @Title: InsightRequestVo
 * @Description：用api请求第一方接口数据
 * @Author： ayf
 * @Date： 2024/3/4
 */
public class InsightRequestVo extends JSONObject {

    public InsightRequestVo(JSONObject obj) {
        //获取基础MapVo
        JSONObject mapVo = JSONObject.from(MapVo.builder().build());
        if (obj != null) {
            mapVo.putAll(obj);
        }
        this.put("mapVo", mapVo);
    }

    @Builder
    @Getter
    public static class MapVo {
        @JSONField
        @Builder.Default
        Integer page = 1;
        @JSONField
        @Builder.Default
        Integer size = 10;
        @JSONField(name = "field_flag")
        @Builder.Default
        String fieldFlag = "1";
        @JSONField
        @Builder.Default
        String modelKey = "MAIN_12345";
        @JSONField
        @Builder.Default
        String action = "all";
        @JSONField
        @Builder.Default
        JSONObject dateRange = new JSONObject() {
            {
                put("creatDate", getTimeString());
            }
        };

        static String getTimeString() {
            LocalDateTime ldt = LocalDateTime.now();
            String todayMin = LocalDateTime.of(ldt.getYear(), ldt.getMonthValue(), ldt.getDayOfMonth(), 0, 0, 0).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String todayMax = LocalDateTime.of(ldt.getYear(), ldt.getMonthValue(), ldt.getDayOfMonth(), 23, 59, 59).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            return todayMin + ',' + todayMax;
        }
    }

}
