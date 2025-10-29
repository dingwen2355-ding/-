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
package com.jslc.modules.szbh.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jslc.modules.szbh.entity.BhFile;
import com.jslc.modules.szbh.entity.BhNotice;
import com.jslc.modules.szbh.service.IFileService;
import com.jslc.modules.szbh.vo.BhNoticeRestVO;
import com.jslc.modules.szbh.vo.BhNoticeVO;
import com.jslc.modules.szbh.mapper.BhNoticeMapper;
import com.jslc.modules.szbh.service.IBhNoticeService;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.core.tool.utils.Func;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 通知公告 服务实现类
 *
 * @author BladeX
 * @since 2023-08-10
 */
@Service
public class BhNoticeServiceImpl extends ServiceImpl<BhNoticeMapper, BhNotice> implements IBhNoticeService {
	@Autowired
	private IFileService fileService;

	@Override
	public BhNoticeVO getDetail(Long id) {
		BhNoticeVO bhNoticeVO = new BhNoticeVO();
		LocalDate data = LocalDate.now();
		BhNotice one = this.getById(id);
		BeanUtil.copy(one,bhNoticeVO);
		if (bhNoticeVO.getFiles() != null && bhNoticeVO.getFiles().length() >0){
			bhNoticeVO.setFileList(fileService.listByIds(Func.toLongList(bhNoticeVO.getFiles())));
		}
		if (data.compareTo(bhNoticeVO.getReleaseTime()) < 0){//未发布
			bhNoticeVO.setType(0);
		}else {//已发布
			bhNoticeVO.setType(1);
		}

		if (data.compareTo(bhNoticeVO.getReleaseTime()) >= 0 && (bhNoticeVO.getOffShelfTime() == null || data.compareTo(bhNoticeVO.getOffShelfTime()) <= 0)){//未过期
			bhNoticeVO.setIsExpire(0);
		}else if (bhNoticeVO.getOffShelfTime() != null && data.compareTo(bhNoticeVO.getOffShelfTime()) > 0){//已过期
			bhNoticeVO.setIsExpire(1);
		}
		if (Func.isNotEmpty(bhNoticeVO.getNoticeType())){
//			0待发布 1已发布
			bhNoticeVO.setNoticeTypeName(bhNoticeVO.getNoticeType() == 1 ? "服务指南" : "通知公告");
		}
		return bhNoticeVO;
	}

	@Override
	public IPage<BhNoticeVO> selectBhNoticePage(IPage<BhNoticeVO> page,String title,Integer noticeType,Integer type,String start,String end,String downStart,String downEnd,Integer isTop) {
		List<BhNoticeVO> bhNoticeVOS = baseMapper.selectBhNoticePage(page, title, noticeType, type, start, end, downStart, downEnd, isTop);
		LocalDate data = LocalDate.now();
		if (CollectionUtils.isNotEmpty(bhNoticeVOS)){
			for (BhNoticeVO f : bhNoticeVOS) {
				if (f.getFiles() != null && f.getFiles().length() >0){
					f.setFileList(fileService.listByIds(Func.toLongList(f.getFiles())));
				}
				if (data.compareTo(f.getReleaseTime()) < 0){//未发布
					f.setType(0);
				}else {//已发布
					f.setType(1);
				}

				if (data.compareTo(f.getReleaseTime()) >= 0 && (f.getOffShelfTime()== null || data.compareTo(f.getOffShelfTime()) <= 0)){//未过期
					f.setIsExpire(0);
				}else if (f.getOffShelfTime()!= null && data.compareTo(f.getOffShelfTime()) > 0){//已过期
					f.setIsExpire(1);
				}
			}
		}
		return page.setRecords(bhNoticeVOS);
	}


	@Override
	public Boolean submit(BhNotice bhNotice) {
		if (bhNotice.getId() != null){
			bhNotice.setUpdateTime(LocalDateTime.now());
			bhNotice.setUpdateUser(AuthUtil.getUserId());
		}else {
			bhNotice.setCreateTime(LocalDateTime.now());
			bhNotice.setCreateUser(AuthUtil.getUserId());
		}
		return this.saveOrUpdate(bhNotice);
	}


    @Override
    public IPage<BhNoticeRestVO> noticeList(IPage<BhNoticeRestVO> page, Integer noticeType) {
        List<BhNoticeRestVO> bhNoticeVOS = baseMapper.noticeList(page, noticeType, LocalDate.now(), LocalDate.now());
        if (Func.isNotEmpty(bhNoticeVOS)) {
            for (BhNoticeRestVO f : bhNoticeVOS) {
                if (Func.isNotEmpty(f.getFiles())) {
                    List<BhFile> bhFiles = fileService.listByIds(Func.toLongList(f.getFiles()));
                    if (Func.isNotEmpty(bhFiles)) {
                        String join = Func.join(bhFiles.stream().map(BhFile::getFileUrl).collect(Collectors.toList()));
                        f.setFileList(join);
                    }
                }
            }
        }
        return page.setRecords(bhNoticeVOS);
    }

	@Override
	public IPage<BhNoticeVO> getNoticeList(IPage<BhNoticeVO> page, Integer noticeType, String title) {
		List<BhNoticeVO> bhNoticeVOS = baseMapper.getNoticeList(page, noticeType, LocalDate.now(), LocalDate.now(),title);

		return page.setRecords(bhNoticeVOS);
	}
}
