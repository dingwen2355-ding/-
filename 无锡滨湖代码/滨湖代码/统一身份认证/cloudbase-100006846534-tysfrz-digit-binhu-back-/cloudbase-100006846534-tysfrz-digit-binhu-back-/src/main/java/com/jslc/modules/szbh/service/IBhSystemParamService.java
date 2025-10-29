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
import com.jslc.modules.szbh.entity.BhSystemParam;
import com.jslc.modules.szbh.vo.BhSystemParamVO;
import org.springblade.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 滨湖系统参数 服务类
 *
 * @author BladeX
 * @since 2023-05-17
 */
public interface IBhSystemParamService extends IService<BhSystemParam> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param bhSystemParam
	 * @return
	 */
	IPage<BhSystemParamVO> selectBhSystemParamPage(IPage<BhSystemParamVO> page, BhSystemParamVO bhSystemParam);

}
