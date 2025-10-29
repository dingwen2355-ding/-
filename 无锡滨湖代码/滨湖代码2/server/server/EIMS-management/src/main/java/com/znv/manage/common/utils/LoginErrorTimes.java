package com.znv.manage.common.utils;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 安全策略：登录接口错误次数
 * @author ：ygr
 * @date ：Created in 2021-1-7
 */

public class LoginErrorTimes {

    private static AtomicInteger errorTimes = new AtomicInteger(0);

    /**
     * 添加错误次数
     */
    public static void addTimes(){
        errorTimes.getAndIncrement();
    }

    /**
     * 登录成功清空错误次数
     */
    public static void clearTimes(){
        errorTimes.set(0);
    }

    /**
     * 超时清空错误次数
     */
    public static void clearTimesByTime(int maxTimes){
        if (errorTimes.get()>= maxTimes){
            errorTimes.set(0);
        }

    }

    /**
     * 判断错误次数是否大于指定阈值
     */
    public static boolean isMoreThan(int maxTimes){
        return errorTimes.get() >= maxTimes;
    }

}
