package com.znv.manage.common.bean;

import lombok.Data;

/**
 * @Title: AlarmStatisticsResultBean
 * @Description:
 * @Author: 李贝德
 * @Date: 2021/7/15 18:48
 **/
@Data
public class AlarmStatisticsResultBean {

    private String count;

    private String eventType;

    private Float rate;
}
