package com.znv.manage.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.znv.manage.common.bean.Result;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author znv
 * @ClassName:
 * @Description: 封装公共方法
 * @date 2018/5/17 14:43
 */
@Component
@Slf4j
public class CommonUtils {
    /**
     * @Fields logger : 日志
     */
    private static Logger logger = LoggerFactory.getLogger(CommonUtils.class);

    /**
     * 获取payLoad 数据
     *
     * @param request request
     * @return payload 参数
     * @throws IOException 异常
     */
    public static String getPayloadRequest(HttpServletRequest request) throws IOException {
        String body = null;
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;
        try {
            InputStream inputStream = request.getInputStream();
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            } else {
                stringBuilder.append("");
            }
        } catch (IOException ex) {
            logger.error("", ex);
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    logger.error("", ex);
                }
            }
        }
        body = stringBuilder.toString();
        //logger.info("by payload get params={}", body);
        return body;
    }

    /**
     * 检查对象是否是数组
     *
     * @param obj 对象
     * @return
     */
    public static boolean isArray(Object obj) {
        return (obj != null && obj.getClass().isArray());
    }

    /**
     * 检查字节数组是否为空
     *
     * @param array
     * @return
     */
    public static boolean isEmpty(byte[] array) {
        return (array == null || array.length == 0);
    }

    /**
     * @param json String
     * @return true/false
     * @Title: isJsonObj
     * @Description: 判断是否是json 格式的字符串
     */
    public static boolean isJsonObj(String json) {
        if (null == json || "".equals(json)) {
            return false;
        }
        try {
            JSONObject.parseObject(json);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * @param json String
     * @return true/false
     * @Title: isJsonArray
     * @Description: 判断是否是json 格式的数组
     */
    public static boolean isJsonArray(String json) {
        if (null == json || "".equals(json)) {
            return false;
        }
        try {
            JSONObject.parseArray(json);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断比较值是否为空，为空则赋值
     *
     * @param object       比较对象值
     * @param defaultValue 设置默认对象值
     * @return 新值
     */
    public static Object defaultJudge(Object object, Object defaultValue) {
        return StringUtils.isEmpty(object) ? defaultValue : object;
    }

    /**
     * 获取格式化时间
     *
     * @param format 格式化类型  yyyy-MM-dd HH:mm:ss/YYYYMMddhhmmss
     * @return 格式化后的时间
     */
    public static String getCurrentDate(String format) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String dateNowStr = sdf.format(date);
        return dateNowStr;
    }

    /**
     * 原格式时间数据转成新格式数据
     *
     * @param time      时间
     * @param oldFormat 原格式类型
     * @param newFormat 现格式类型
     * @return 新格式时间类型
     */
    public static String getFormatTime(String time, String oldFormat, String newFormat) {
        String newTime = "";
        try {
            SimpleDateFormat oldSdf = new SimpleDateFormat(oldFormat);
            SimpleDateFormat newSdf = new SimpleDateFormat(newFormat);
            Date oldDate = null;
            oldDate = oldSdf.parse(time);
            newTime = newSdf.format(oldDate);
        } catch (ParseException e) {
            logger.error("时间格式转换出错   time={}", time);
        }
        return newTime;
    }

    public static String mathRandom(int count) {
        String math = "";
        if (count > 0) {
            while (math.length() < count) {
                math += (int) (Math.random() * 10);
            }
        }
        return math;

    }


    /**
     * 获取前端数据 参数
     *
     * @param request
     * @return
     * @throws Exception
     */
    public static String getStringData(HttpServletRequest request) throws Exception {
        InputStream in = null;
        BufferedInputStream buf = null;
        byte[] content = null;
        try {
            in = request.getInputStream();
            buf = new BufferedInputStream(in);
            int buffSize = 1024;
            ByteArrayOutputStream out = new ByteArrayOutputStream(buffSize);
            byte[] temp = new byte[buffSize];
            int size = 0;
            while ((size = buf.read(temp)) != -1) {
                out.write(temp, 0, size);
            }
            content = out.toByteArray();

        } catch (Exception e) {
            logger.error("", e);
        } finally {
            try {
                if (null != in) {
                    in.close();
                }
                if (null != buf) {
                    buf.close();
                }
            } catch (IOException e) {
                logger.error("", e);
            }
        }
        return new String(content, "utf-8");
    }

    /**
     * InputStream --> File
     *
     * @param ins
     * @param file
     * @throws Exception
     */
    public static void inputstreamToFile(InputStream ins, File file) {
        OutputStream os = null;
        try {
            os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            logger.error("", e);
        } finally {
            if (null != os) {
                try {
                    os.close();
                } catch (IOException e) {
                    logger.error("", e);
                }
            }
            if (null != ins) {
                try {
                    ins.close();
                } catch (IOException e) {
                    logger.error("", e);
                }
            }
        }
    }


    /**
     * 检查是否是本机
     *
     * @param ipStr ip
     * @return
     */
    public boolean isLocalIp(String ipStr) {
        try {
            Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
            while (en.hasMoreElements()) {
                NetworkInterface ni = en.nextElement();
                Enumeration<InetAddress> ips = ni.getInetAddresses();
                while (ips.hasMoreElements()) {
                    InetAddress ip = (InetAddress) ips.nextElement();
                    if (ip.isSiteLocalAddress()
                            && !ip.isLoopbackAddress()   //127.开头的都是lookback地址
                            && ip.getHostAddress().indexOf(":") == -1
                            && ipStr.equals(ip.getHostAddress())) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            logger.error("检查是否是本机异常！", e);
        }
        return false;
    }


    /**
     * @param v
     * @return
     */
    public static String getValue(Object v) {
        return v == null ? "" : v.toString();
    }

    /**
     * 读配置文件
     *
     * @param projectDirPath 文件路径
     * @return
     */
    public static String readFileContent(String projectDirPath) {
        BufferedReader br = null;
        String content = "";
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(projectDirPath))));
            String line = null;
            while ((line = br.readLine()) != null) {
                content += line + "\n";
            }
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (Exception e) {
                logger.error("", e);
            }
        }
        return content;
    }

    /**
     * 下载文件
     *
     * @param name     文件名
     * @param path     文件路径
     * @param response response
     */
    public static void downFileByPath(String name, String path, HttpServletResponse response) {
        try {
            // 设置响应的编码方式;
            response.setCharacterEncoding("utf-8");
            try {
                response.addHeader("Content-Disposition", "attachment;filename="
                        + URLEncoder.encode(name, "utf-8"));
                response.setContentType("application/zip;charset=utf-8");
                // excel模板所在的位置;
            } catch (UnsupportedEncodingException e) {
                logger.error("", e);
            }
            BufferedInputStream bis = null;
            BufferedOutputStream bos = null;
            try {

                bis = new BufferedInputStream(new FileInputStream(new File(path)));
                bos = new BufferedOutputStream(response.getOutputStream());
                int size = 0;
                byte[] buff = new byte[1024];
                while ((size = bis.read(buff)) > 0) {
                    bos.write(buff, 0, size);
                }
                bos.flush();
            } catch (Exception e) {
                logger.error("", e);
            } finally {
                if (bos != null) {
                    try {
                        bos.close();
                    } catch (Exception e) {
                        logger.error("bos.close is fail.", e);
                    }
                }

                if (bis != null) {
                    try {
                        bis.close();
                    } catch (Exception e) {
                        logger.error("bis.close is fail.", e);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    /**
     * 获取前多少分钟时间
     *
     * @param format      转换成的时间格式 YYYYMMddHHmmss
     * @param forwardTime 前多少分钟
     * @return
     */
    public String getBeforeTime(String format, int forwardTime) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Calendar beforeTime = Calendar.getInstance();
        // xx分钟之前的时间
        beforeTime.add(Calendar.MINUTE, -forwardTime);
        Date beforeD = beforeTime.getTime();
        String time = sdf.format(beforeD);
        return time;
    }

    /**
     * 比较数值型字符串大小
     *
     * @param version1
     * @param version2
     * @return
     */
    public static int compareVersion(String version1, String version2) {

        String[] v1 = version1.split("\\.");
        String[] v2 = version2.split("\\.");

        for (int i = 0; i < Math.max(v1.length, v2.length); i++) {
            int num1 = i < v1.length ? Integer.parseInt(v1[i]) : 0;
            int num2 = i < v2.length ? Integer.parseInt(v2[i]) : 0;
            if (num1 < num2) {
                return -1;
            } else if (num1 > num2) {
                return 1;
            }
        }
        return 0;
    }

    /**
     * 提供精确的加法运算。
     *
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    /**
     * 提供精确的减法运算。
     *
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static double sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 除法，并格式化结果
     *
     * @param v1     除数
     * @param v2     被除数
     * @param format 数字格式化类型 00.00，0.00%等
     * @return
     */
    public static String percentFormat(String v1, String v2, String format) {
        if (!StringUtils.isEmpty(v1) && !StringUtils.isEmpty(v2)) {
            DecimalFormat decimalFormat = new DecimalFormat(format);
            double percent = Double.parseDouble(v1) / Double.parseDouble(v2) * 100;
            return decimalFormat.format(percent);
        }
        return "";
    }

    public static Map<String, List<Map>> changeMap(List<Map<String, String>> lists, String key) {
        Map<String, List<Map>> result = new HashMap<>();
        for (Map list : lists) {
            String score_date = list.get(key).toString();
            if (result.containsKey(score_date)) {
                List<Map> maps = result.get(score_date);
                maps.add(list);
                result.put(score_date, maps);
            } else {
                List<Map> maps = new ArrayList<>();
                maps.add(list);
                result.put(score_date, maps);
            }
        }
        return result;
    }

    /**
     * 将数字格式化
     *
     * @param v
     * @param format
     * @return
     */
    public static String percentFormat(float v, String format) {
        if (!StringUtils.isEmpty(v)) {
            DecimalFormat decimalFormat = new DecimalFormat(format);
            return decimalFormat.format(v);
        }
        return "";
    }

    public static LinkedHashMap<String, List<Map>> changeMap3(List<Map<String, String>> lists, String key) {
        LinkedHashMap<String, List<Map>> result = new LinkedHashMap<>();
        for (Map list : lists) {
            String score_date = list.get(key).toString();
            if (result.containsKey(score_date)) {
                List<Map> maps = result.get(score_date);
                maps.add(list);
                result.put(score_date, maps);
            } else {
                List<Map> maps = new ArrayList<>();
                maps.add(list);
                result.put(score_date, maps);
            }
        }
        return result;
    }

    public static Map<String, String> changeMap2(List<Map> lists, String key, String key2) {
        Map<String, String> result = new HashMap<>();
        for (Map list : lists) {
            String s = list.get(key).toString();
            String s1 = list.get(key2).toString();
            result.put(s, s1);
        }
        return result;
    }

    public static Map<String, List<Map>> changeMapT(List<Map> lists, String key) {
        Map<String, List<Map>> result = new HashMap<>();
        for (Map list : lists) {
            if (list.get(key) != null) {
                String score_date = list.get(key).toString();
                if (result.containsKey(score_date)) {
                    List<Map> maps = result.get(score_date);
                    maps.add(list);
                    result.put(score_date, maps);
                } else {
                    List<Map> maps = new ArrayList<>();
                    maps.add(list);
                    result.put(score_date, maps);
                }
            }
        }
        return result;
    }

    public static String uploadFile(MultipartFile file, String fileLoad, String urlPath) {
        Result result = new Result();
        InputStream inputStream = null;
        OutputStream os = null;
        try {
            if (file != null) {
                //String fileName = file.getOriginalFilename();
                String fileName = file.getOriginalFilename();
                //int num = (int)(new Random().nextDouble()*10000);
                String name = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(Calendar.getInstance().getTime()) + "-" + fileName;
                //String name = UUID.randomUUID().toString().replace("-","") + num;
                //String exts = fileName.substring(fileName.lastIndexOf(".") + 1);
                inputStream = file.getInputStream();
                byte[] bs = new byte[1024];
                // 读取到的数据长度
                int len;
                // 输出的文件流保存到本地文件
                String date = DateUtil.getStringDateShort();
                File tempFile = new File(fileLoad + File.separator + date);
                if (!tempFile.exists()) {
                    tempFile.mkdirs();
                }
                String filePath = tempFile.getPath() + File.separator + name;
                os = new FileOutputStream(filePath);
                // 开始读取
                while ((len = inputStream.read(bs)) != -1) {
                    os.write(bs, 0, len);
                }
                filePath = filePath.replaceAll("\\\\","/");
                String replace = filePath.replace(fileLoad, urlPath);
                return replace;
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.error("uploadFile is fail. e:{}", e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e) {
                    logger.error("inputStream.close is fail.", e);
                }
            }
            if (os != null) {
                try {
                    os.close();
                } catch (Exception e) {
                    logger.error("os.close is fail.", e);
                }
            }
        }
        return null;
    }

    public static Boolean deleteFile(String fileUrl, String fileLoad, String urlPath) {
        if ((fileUrl == null) || ("".equals(fileUrl))) {
            return true;
        }
        String[] path = fileUrl.split(";");
        for (int i = 0; i < path.length; i++) {
            String replace = path[i].replace(urlPath, fileLoad);
            File file = new File(replace);
            //文件是否存在
            if (file.exists()) {
                try {
                    if (!file.delete()) {
                        logger.error("文件删除失败");
                        return false;
                    }
                } catch (Exception e) {
                    logger.error("deleteFile is fail. e:{}", e);
                    return false;
                }
            }
        }
        return true;
    }

    public static String MD5(String s) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(s.getBytes("utf-8"));
            final char[] HEX_DIGITS = "0123456789abcdef".toCharArray();
            StringBuilder ret = new StringBuilder(bytes.length * 2);
            for (int i = 0; i < bytes.length; i++) {
                ret.append(HEX_DIGITS[(bytes[i] >> 4) & 0x0f]);
                ret.append(HEX_DIGITS[bytes[i] & 0x0f]);
            }
            return ret.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 汉字转为拼音
     *
     * @param chinese
     * @return
     */
    public static String toPinyin(String chinese) {
        String pinyinStr = "";
        char[] newChar = chinese.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < newChar.length; i++) {
            if (newChar[i] > 128) {
                try {
                    pinyinStr += PinyinHelper.toHanyuPinyinStringArray(newChar[i], defaultFormat)[0];
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    logger.error(e.toString());
                }
            } else {
                pinyinStr += newChar[i];
            }
        }
        return pinyinStr;
    }

    public static double getDistance(String gpsx1, String gpsy1, String gpsx2, String gpsy2) {
        double lon1 = (Math.PI / 180) * Double.parseDouble(gpsx1);
        double lon2 = (Math.PI / 180) * Double.parseDouble(gpsx2);
        double lat1 = (Math.PI / 180) * Double.parseDouble(gpsy1);
        double lat2 = (Math.PI / 180) * Double.parseDouble(gpsy2);
        double R = 6371;// 地球半径
        double d = R * Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon2 - lon1));
        return d;//返回单位：米
    }

    public static boolean checkFileName(MultipartFile uploadFile, String fileNameWhite) {
        if (uploadFile == null) {
            return false;
        }
        String fileName = uploadFile.getOriginalFilename();
        if (StringUtils.isEmpty(fileName)) {
            log.error("uploadFile fileName is null.");
            return false;
        }
        //使用lastIndexOf()结合subString()获取后缀名
        log.info("uploadFile fileName:{}", fileName);
        if (fileName.lastIndexOf(".") == -1) {
            return false;
        }
        return fileNameWhite.contains(fileName.substring(fileName.lastIndexOf(".")));
    }

    private static final double EARTH_RADIUS = 6371.0; // 地球半径（单位：千米）

    // 计算两个经纬度之间的距离
    public static double calculateDistance(String lat1Str, String lon1Str, String lat2Str, String lon2Str) {
        double lat1;
        double lon1;
        double lat2;
        double lon2;

        try {
            lat1 = Double.parseDouble(lat1Str);
            lon1 = Double.parseDouble(lon1Str);
            lat2 = Double.parseDouble(lat2Str);
            lon2 = Double.parseDouble(lon2Str);
        } catch (NumberFormatException e) {
            return 0;
        }

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c;
    }
}
