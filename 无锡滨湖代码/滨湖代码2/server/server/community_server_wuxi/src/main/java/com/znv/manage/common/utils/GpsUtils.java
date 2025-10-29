package com.znv.manage.common.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.HashMap;
import java.util.Map;


/**
 * 坐标转换工具类
 * @author fxq
 */

@Slf4j
public class GpsUtils {
    /**
     * 高德apiKEY
     */
    public static String GAODE_API_KEY = "6c6e3800f80b0604935c0b3c2c1391b6";
    public static double pi = 3.1415926535897932384626;
    public static double x_pi = 3.14159265358979324 * 3000.0 / 180.0;
    public static double a = 6378245.0;
    public static double ee = 0.00669342162296594323;

    public static double transformLat(double x, double y) {
        double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(y * pi) + 40.0 * Math.sin(y / 3.0 * pi)) * 2.0 / 3.0;
        ret += (160.0 * Math.sin(y / 12.0 * pi) + 320 * Math.sin(y * pi / 30.0)) * 2.0 / 3.0;
        return ret;
    }

    public static double transformLon(double x, double y) {
        double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(x * pi) + 40.0 * Math.sin(x / 3.0 * pi)) * 2.0 / 3.0;
        ret += (150.0 * Math.sin(x / 12.0 * pi) + 300.0 * Math.sin(x / 30.0 * pi)) * 2.0 / 3.0;
        return ret;
    }
    public static double[] transform(double lat, double lon) {
        if (outOfChina(lat, lon)) {
            return new double[]{lat,lon};
        }
        double dLat = transformLat(lon - 105.0, lat - 35.0);
        double dLon = transformLon(lon - 105.0, lat - 35.0);
        double radLat = lat / 180.0 * pi;
        double magic = Math.sin(radLat);
        magic = 1 - ee * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * pi);
        dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * pi);
        double mgLat = lat + dLat;
        double mgLon = lon + dLon;
        return new double[]{mgLat,mgLon};
    }
    /**
     * 判断是否在中国
     * @param lat
     * @param lon
     * @return
     */
    public static boolean outOfChina(double lat, double lon) {
        if (lon < 72.004 || lon > 137.8347)
            return true;
        if (lat < 0.8293 || lat > 55.8271)
            return true;
        return false;
    }
    /**
     * 84 ==》 高德
     * @param lat
     * @param lon
     * @return
     */
    public static double[] gps84_To_Gcj02(double lat, double lon) {
        if (outOfChina(lat, lon)) {
            return new double[]{lat,lon};
        }
        double dLat = transformLat(lon - 105.0, lat - 35.0);
        double dLon = transformLon(lon - 105.0, lat - 35.0);
        double radLat = lat / 180.0 * pi;
        double magic = Math.sin(radLat);
        magic = 1 - ee * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * pi);
        dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * pi);
        double mgLat = lat + dLat;
        double mgLon = lon + dLon;
        return new double[]{mgLat, mgLon};
    }

    /**
     * 高德 ==》 84
     * @param lon * @param lat * @return
     * */
    public static double[] gcj02_To_Gps84(double lat, double lon) {
        double[] gps = transform(lat, lon);
        double lontitude = lon * 2 - gps[1];
        double latitude = lat * 2 - gps[0];
        return new double[]{latitude, lontitude};
    }
    /**
     * 高德 == 》 百度
     * @param lat
     * @param lon
     */
    public static double[] gcj02_To_Bd09(double lat, double lon) {
        double x = lon, y = lat;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_pi);
        double tempLon = z * Math.cos(theta) + 0.0065;
        double tempLat = z * Math.sin(theta) + 0.006;
        double[] gps = {tempLat,tempLon};
        return gps;
    }

    /**
     * 百度 == 》 高德
     * @param lat
     * @param lon
     */
    public static double[] bd09_To_Gcj02(double lat, double lon) {
        double x = lon - 0.0065, y = lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);
        double tempLon = z * Math.cos(theta);
        double tempLat = z * Math.sin(theta);
        double[] gps = {tempLat,tempLon};
        return gps;
    }

    /**
     * 84 == 》 百度
     * @param lat
     * @param lon
     * @return
     */
    public static double[] gps84_To_bd09(double lat,double lon){
        double[] gcj02 = gps84_To_Gcj02(lat,lon);
        double[] bd09 = gcj02_To_Bd09(gcj02[0],gcj02[1]);
        return bd09;
    }

    /**
     * 百度 == 》 84
     * @param lat
     * @param lon
     * @return
     */
    public static double[] bd09_To_gps84(double lat,double lon){
        double[] gcj02 = bd09_To_Gcj02(lat, lon);
        double[] gps84 = gcj02_To_Gps84(gcj02[0], gcj02[1]);
        //保留小数点后六位
        gps84[0] = retain6(gps84[0]);
        gps84[1] = retain6(gps84[1]);
        return gps84;
    }

    /**
     * 地址转高德gps坐标
     * @param city : 查询城市，可选：城市中文、中文全拼
     * @param address : 填写结构化地址信息:省份＋城市＋区县＋城镇＋乡村＋街道＋门牌号码
     * @return
     */

    public static Map<String,String> getGps(String city,String address){
        Map<String,String> gpsMap = new HashMap<>();
        gpsMap.put("lon","");
        gpsMap.put("lat","");
        if(StringUtils.isEmpty(address)){
            return gpsMap;
        }
        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = "https://restapi.amap.com:13443/v3/geocode/geo?key="+GAODE_API_KEY+"&city="+city+"&address=";
        String result = restTemplate.getForObject(apiUrl + address, String.class);
        if(!StringUtils.isEmpty(result)){
            JSONObject resultObject = JSONObject.parseObject(result);
            if(!StringUtils.isEmpty(resultObject) && "1".equals(resultObject.get("status"))){
                JSONArray geocodesArray = resultObject.getJSONArray("geocodes");
                if(!CollectionUtils.isEmpty(geocodesArray)){
                    String location = geocodesArray.getJSONObject(0).getString("location");
                    if(!StringUtils.isEmpty(location)){
                        String[] gps = location.split(",");
                        gpsMap.put("lon",gps[0]);
                        gpsMap.put("lat",gps[1]);
                    }
                }
            }else {
                log.info("高德api转换地址到gps失败");
            }
        }
        return gpsMap;
    }

    /*
     * 保留小数点后六位
     * @param num
     * @return
     */
    private static double retain6(double num){
        String result = String .format("%.6f", num);
        return Double.valueOf(result);
    }


    public static void main(String args[]){

        Map<String, String> s = getGps("上海市", "上海市浦东新区航头镇牌楼村20组谈弄610号");

        double[] d = gcj02_To_Gps84(Double.valueOf(s.get("lat")), Double.valueOf(s.get("lon")));
    }
}
