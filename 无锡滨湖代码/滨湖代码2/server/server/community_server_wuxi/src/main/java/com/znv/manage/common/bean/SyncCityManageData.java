package com.znv.manage.common.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class SyncCityManageData {
    private String id;
    private String WMS_address;
    private String map_catalogue;
    private String coordinate_system;
    private String map_name;
    private String collect_time;
    private String department;
    private String category;
    private String REST_address;
}
