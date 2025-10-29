package com.jslc.modules.system.service.impl;

import lombok.AllArgsConstructor;
import com.jslc.modules.system.service.ILogApiService;
import com.jslc.modules.system.service.ILogErrorService;
import org.springblade.core.log.model.LogApi;
import org.springblade.core.log.model.LogError;
import org.springblade.core.log.model.LogUsual;
import com.jslc.modules.system.service.ILogService;
import com.jslc.modules.system.service.ILogUsualService;
import org.springframework.stereotype.Service;

/**
 * Created by Blade.
 *
 * @author zhuangqian
 */
@Service
@AllArgsConstructor
public class LogServiceImpl implements ILogService {

	private final ILogUsualService usualService;
	private final ILogApiService apiService;
	private final ILogErrorService errorService;

	@Override
	public Boolean saveUsualLog(LogUsual log) {
		return usualService.save(log);
	}

	@Override
	public Boolean saveApiLog(LogApi log) {
		return apiService.save(log);
	}

	@Override
	public Boolean saveErrorLog(LogError log) {
		return errorService.save(log);
	}

}
