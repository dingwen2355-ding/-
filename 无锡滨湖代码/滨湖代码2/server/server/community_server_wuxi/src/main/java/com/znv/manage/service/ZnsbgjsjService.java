package com.znv.manage.service;


import com.znv.manage.common.bean.Result;

import java.util.Date;

public interface ZnsbgjsjService {

    Result getZnsbgjsjStatistics(Date beginTime, Date endTime);

    int getData();
}
