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
package com.jslc.modules.szbh.service;

import com.baomidou.mybatisplus.extension.service.IService;

import com.jslc.modules.szbh.vo.HeadActiveVO;
import com.jslc.modules.szbh.vo.HeadPageVO;

import java.util.List;
import java.util.Map;

/**
 * 空气大屏 服务类
 *
 * @author BladeX
 * @since 2020-09-03
 */
public interface IHeadPageService {


    List<HeadPageVO> getBulletin();


    List<HeadPageVO> getAppRecordRank();


    HeadActiveVO getYearActive();


    Map<String, Object> getUserInfo();


}
