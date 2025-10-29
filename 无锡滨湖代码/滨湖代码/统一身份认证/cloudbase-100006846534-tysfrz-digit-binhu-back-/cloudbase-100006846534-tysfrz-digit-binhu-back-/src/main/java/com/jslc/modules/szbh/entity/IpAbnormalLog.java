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
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.mp.base.BaseEntity;

import java.time.LocalDateTime;

/**
 * ip相同动作异常记录表实体类
 *
 * @author BladeX
 * @since 2023-10-09
 */
@Data
@TableName("bh_ip_abnormal_log")
public class IpAbnormalLog  {

    private static final long serialVersionUID = 1L;

    private Long id;
    /**
     * 请求者ip
     */
    private String requestIp;
    /**
     * 请求接口名称
     */
    private String requestInterface;
    /**
     * 接口动作
     */
    private String interfaceType;
    /**
     * 时间戳
     */
    private Long requestTime;
    /**
     * 返回结果
     */
    private String result;
    /**
     * app_id
     */
    private String appId;
	/**
	 * 创建时间
	 */
	private LocalDateTime createTime;


}
