/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.jslc.modules.job.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jslc.modules.job.entiry.ScheduleJobEntity;
import org.springframework.data.redis.connection.Message;


import java.util.Map;

/**
 * 定时任务
 *
 * @author Mark sunlightcs@gmail.com
 */
public interface ScheduleJobService extends IService<ScheduleJobEntity> {

	IPage queryPage(Map<String, Object> params,IPage page);

	/**
	 * 保存定时任务
	 */
	void saveJob(ScheduleJobEntity scheduleJob);

	/**
	 * 更新定时任务
	 */
	void update(ScheduleJobEntity scheduleJob);

	/**
	 * 批量删除定时任务
	 */
	void deleteBatch(long[] jobIds);

	/**
	 * 批量更新定时任务状态
	 */
	int updateBatch(Long[] jobIds, int status);

	/**
	 * 立即执行
	 */
	void run(Long[] jobIds);

	/**
	 * 暂停运行
	 */
	void pause(Long[] jobIds);

	/**
	 * 恢复运行
	 */
	void resume(Long[] jobIds);

	void deleteJob(String message);
	void updateJob(String message);
	/*根据业务id获取job*/
	ScheduleJobEntity getJobByBizId(Long bizId);
}
