package com.znv.manage.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class MapUtil {

    private static double PI = 3.14159265358979324;

    public static double a = 6378245.0;
    public static double ee = 0.00669342162296594323;

    private static Logger logger = LoggerFactory.getLogger(MapUtil.class);

    /**
     * 84 to 火星/高德坐标系 (GCJ-02)
     *
     * @param lat
     * @param lon
     * @return
     */
    public static Map<String,Double> gps84_To_Gcj02(double lon, double lat) {
        if (outOfChina(lon, lat)) {
            return null;
        }
        double dLat = transformLat(lon - 105.0, lat - 35.0);
        double dLon = transformLon(lon - 105.0, lat - 35.0);
        double radLat = lat / 180.0 * PI;
        double magic = Math.sin(radLat);
        magic = 1 - ee * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * PI);
        dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * PI);
        double mgLat = lat + dLat;
        double mgLon = lon + dLon;
        Map<String, Double> gps = new HashMap<>();
        gps.put("lon",mgLon);
        gps.put("lat",mgLat);
        return gps;
    }

    /**
     * * 火星/高德坐标系 (GCJ-02) to 84 * * @param lon * @param lat * @return
     * */
    public static Map<String,Double> gcj_To_Gps84(double lon, double lat) {
        Map<String,Double> gps = transform(lon, lat);
        gps.put("lon",lon * 2-gps.get("lon"));
        gps.put("lat",lat * 2-gps.get("lat"));
        return gps;
    }

    public static boolean outOfChina(double lon, double lat) {
        if (lon < 72.004 || lon > 137.8347)
            return true;
        if (lat < 0.8293 || lat > 55.8271)
            return true;
        return false;
    }


    public static Map<String,Double> transform(double lon, double lat) {
        Map<String, Double> gps = new HashMap<>();
        if (outOfChina(lon, lat)) {
            gps.put("lon",lon);
            gps.put("lat",lat);
            return gps;
        }
        double dLat = transformLat(lon - 105.0, lat - 35.0);
        double dLon = transformLon(lon - 105.0, lat - 35.0);
        double radLat = lat / 180.0 * PI;
        double magic = Math.sin(radLat);
        magic = 1 - ee * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * PI);
        dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * PI);
        double mgLat = lat + dLat;
        double mgLon = lon + dLon;
        gps.put("lon",mgLon);
        gps.put("lat",mgLat);
        return gps;
    }


    //转换经度
    private static double transformLon(double x, double y) {
        double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * PI) + 20.0 * Math.sin(2.0 * x * PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(x * PI) + 40.0 * Math.sin(x / 3.0 * PI)) * 2.0 / 3.0;
        ret += (150.0 * Math.sin(x / 12.0 * PI) + 300.0 * Math.sin(x / 30.0 * PI)) * 2.0 / 3.0;
        return ret;
    }


    //转换纬度
    private static double transformLat(double x, double y) {
        double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * PI) + 20.0 * Math.sin(2.0 * x * PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(y * PI) + 40.0 * Math.sin(y / 3.0 * PI)) * 2.0 / 3.0;
        ret += (160.0 * Math.sin(y / 12.0 * PI) + 320 * Math.sin(y * PI / 30.0)) * 2.0 / 3.0;
        return ret;
    }

}
