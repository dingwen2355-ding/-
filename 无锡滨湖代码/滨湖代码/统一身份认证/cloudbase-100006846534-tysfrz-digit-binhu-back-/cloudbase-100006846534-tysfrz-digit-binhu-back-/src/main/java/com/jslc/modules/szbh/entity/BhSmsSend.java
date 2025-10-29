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
package com.jslc.modules.szbh.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springblade.core.tool.utils.DateUtil;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 短信通知记录实体类
 *
 * @author BladeX
 * @since 2023-05-18
 */
@Data
@TableName("bh_sms_send")
public class BhSmsSend {

    private static final long serialVersionUID = 1L;

	/**
	 * 通知人
	 */
	private Long id;
    /**
     * 通知人
     */
    private String noticePerson;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 通知时间
     */
    @JsonFormat(pattern = DateUtil.PATTERN_DATETIME)
    private LocalDateTime noticeTime;
    /**
     * 事件类型
     */
    private String eventType;
    /**
     * 应用
     */
    private String application;
    /**
     * 通知内容
     */
    private String content;
    /**
     * 结果，1-成功，0-失败
     */
    private Integer result;
    /**
     * appId
     */
    private String appId;
	/**
	 * 创建时间
	 */
	private LocalDateTime createTime;



    /**
     * 实时和即时发送id
     */
    @ApiModelProperty(value = "实时和即时发送id")
    private Long settingId;

}
