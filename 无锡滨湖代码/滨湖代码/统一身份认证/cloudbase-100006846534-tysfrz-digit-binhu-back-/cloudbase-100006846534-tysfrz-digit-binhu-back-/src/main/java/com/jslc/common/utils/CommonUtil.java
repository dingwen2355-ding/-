/*
 *      Copyright (c) 2018-2028, Chill Zhuang All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the dreamlu.net developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: Chill 庄骞 (smallchill@163.com)
 */
package com.jslc.common.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.core.tool.utils.Func;

import java.util.ArrayList;
import java.util.List;

/**
 * 通用工具类
 *
 * @author Chill
 */
public class CommonUtil {

    /**
     * @param page
     * @param list
     * @return com.baomidou.mybatisplus.core.metadata.IPage<org.apache.poi.ss.formula.functions.T>
     * @description 人为分页
     * @author xxz
     * @date 2021/1/14
     **/
    public static IPage manualPage(IPage page, List list) {

        List resultList = new ArrayList();

        if (Func.isNotEmpty(list)) {
            Long iCurrent = page.getCurrent();
            Long iSize = page.getSize();
            Long startRow = (iCurrent - 1) * iSize;
            Long endRow = iCurrent * iSize;
            Long total = (long) (list.size());

            for (Long i = startRow; i < (endRow > total ? total : endRow); i++) {
                resultList.add(list.get(i.intValue()));
            }
            page.setTotal(total);
        }

        return page.setRecords(resultList);
    }

    /*
     * @method getCronExpression
     * @description 定时表达式替换
     * @author xxz
     * @date 2023/9/26
     * @param time
     */
    public static String getCronExpression(String time) {

        // 截取分钟和小时
        String hour = time.substring(0, 2).equals("00")? "0": time.substring(0, 2).replaceAll("^0+", "");
        String min = time.substring(3, 5).equals("00")? "0": time.substring(3, 5).replaceAll("^0+", "");
        // 替换min,hour
        String cron = "0 min hour ? * *";
        return cron.replaceAll("min", min).replaceAll("hour", hour);
    }
}
