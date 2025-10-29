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
package com.jslc.common.constant;

import org.springblade.core.tool.utils.StringPool;

import java.math.BigDecimal;

/**
 * @author xxz
 * @version 3.7.0
 * @className SwapConstant
 * @description 地表水业务类常量
 * @date 2020/7/20
 * @since JDK 1.8
 **/
public interface SwapConstant {

    /**
     * 报表类别
     */
    enum ReportType {

        //报表类别：小时，日，周，月，年
        hour(1, "小时"),
        day(2, "日"),
        week(3, "周"),
        month(4, "月"),
        year(5, "年"),
        halfHour(6, "半小时"),
        custom(7, "自定义");

        private int value;
        private String desc;

        ReportType(int value, String desc) {
            this.value = value;
            this.desc = desc;
        }

        public int getValue() {
            return value;
        }

        public String getDesc() {
            return desc;
        }

    }

    enum ReviewStatus {


        hour(0, "待审核"),
        day(1, "通过"),
        week(2, "驳回");


        private int value;
        private String desc;

        ReviewStatus(int value, String desc) {
            this.value = value;
            this.desc = desc;
        }

        public int getValue() {
            return value;
        }

        public String getDesc() {
            return desc;
        }

        public static String getDescByValue(int value) {
            for (ReviewStatus wqiLevel : ReviewStatus.values()) {
                if (wqiLevel.getValue()==value) {
                    return wqiLevel.getDesc();
                }
            }
            return "";
        }

    }

}
