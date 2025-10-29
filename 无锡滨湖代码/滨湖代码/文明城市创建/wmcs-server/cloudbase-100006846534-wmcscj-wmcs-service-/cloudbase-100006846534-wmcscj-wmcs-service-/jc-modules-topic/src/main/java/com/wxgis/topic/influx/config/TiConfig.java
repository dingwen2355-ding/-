package com.wxgis.topic.influx.config;

import lombok.Data;

import java.sql.Date;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@Data
public class TiConfig {

    public static String username = "superadmin";
    public static String secretId = "a40c373844364e69b646100a49";
    public static String secretKey = "acd182953fd64cf0ba01a87de0";


    public static String timestamp = String.valueOf(System.currentTimeMillis() / 1000);

    // 街面物体检测分析预警
    public static String topic1 = "app-0010012";
    // 城市道路车辆违规事件分析预警
    public static String topic2 = "app-001002001";

    public static String ip = "2.21.138.49";
    public static int port = 10022;
    //网关接口
    public static String getGateway = "http://2.21.138.49/gateway";
    public static String getDownloadFileUrl = "/DownloadFile?Key=%s&Token=%s&IsTemp=false";

    public static void main(String[] args) {
        System.out.println(timestamp);

    }

}
