package com.jslc.modules.szbh.util;

import com.jslc.common.utils.TokenEncryptUtils;
import com.jslc.modules.szbh.mapper.BhDeptSyncMapper;
import com.jslc.modules.szbh.service.IBhDeptSyncService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

public class TokenUtilsmm {

    private static Map<String,String> MAP_TOKENS = new HashMap<>();
    private static final int VALID_TIME = 60; // token有效期(秒)
//    public static final String TOKEN_ERROR = "F"; // 非法
//    public static final String TOKEN_OVERDUE = "G"; // 过期
//    public static final String TOKEN_FAILURE = "S"; // 失效

    public static final String TOKEN_ERROR = "非法请求"; // 非法
    public static final String TOKEN_OVERDUE = "授权过期"; // 过期
    public static final String TOKEN_FAILURE = "请求失败"; // 失效

    private static final String APP_ID = "c3piaGdldHRva2Vu";//szbhgettoken 将这个base64加密得来
    private static final String APP_SECRET = "b933b14b5fe60b1cf3b4cb347eae831c";//szbhgettoken  将这个md5加密（32位小写）加密得来

    @Autowired
    private BhDeptSyncMapper bhDeptSyncMapper;

    /**
     * 生成token,该token长度不一致,如需一致,可自行MD5或者其它方式加密一下
     * 该方式的token只存在磁盘上,如果项目是分布式,最好用redis存储
     * @param str: 该字符串可自定义,在校验token时要保持一致
     * @return
     */
    public static String getToken(String appId,String appSecret) {
        String token = TokenEncryptUtils.encoded(getCurrentTime()+","+appId,appSecret);
        MAP_TOKENS.put(appId, token);
        return token;
    }

    /**
     * 校验token的有效性
     * @param token
     * @return
     */
    public static String checkToken(String token) {
        if (token == null) {
            return TOKEN_ERROR;
        }
        try{
            String[] tArr = TokenEncryptUtils.decoded(token,APP_SECRET).split(",");
            if (tArr.length != 2) {
                return TOKEN_ERROR;
            }
            // token生成时间戳
            int tokenTime = Integer.parseInt(tArr[0]);
            // 当前时间戳
            int currentTime = getCurrentTime();
            if (currentTime-tokenTime < VALID_TIME) {
                String tokenStr = tArr[1];
                String mToken = MAP_TOKENS.get(tokenStr);
                if (mToken == null) {
                    return TOKEN_OVERDUE;
                } else if(!mToken.equals(token)) {
                    return TOKEN_FAILURE;
                }
                return tokenStr;
            } else {
                return TOKEN_OVERDUE;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return TOKEN_ERROR;
    }

    /**获取当前时间戳（10位整数）*/
    public static int getCurrentTime() {
        return (int)(System.currentTimeMillis()/1000);
    }

    /**
     * 移除过期的token
     */
    public static void removeInvalidToken() {
        int currentTime = getCurrentTime();
        for (Map.Entry<String,String> entry : MAP_TOKENS.entrySet()) {
            String[] tArr = TokenEncryptUtils.decoded(entry.getValue(),APP_SECRET).split(",");
            int tokenTime = Integer.parseInt(tArr[0]);
            if(currentTime-tokenTime > VALID_TIME){
                MAP_TOKENS.remove(entry.getKey());
            }
        }
    }

    /**
     * 测试
     * @param args
     */
    public static void main(String[] args) {
        String str = "username_and_password";

        // 获取token
        String token = TokenUtilsmm.getToken(APP_ID,APP_SECRET);
        System.out.println("token Result: " + token);

        // 校验token
        String checkToken = TokenUtilsmm.checkToken(token);
        System.out.println("checkToken Result: " + checkToken);
        if(str.equals(checkToken)) {
            System.out.println("==>token verification succeeded!");
        }

    }

}
