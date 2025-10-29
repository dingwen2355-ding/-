package com.jslc.modules.job.task;

import cn.hutool.core.util.IdUtil;
import com.jslc.common.utils.SquirrelRedis;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author zhengyongbiao
 * @title: AbstractTask
 * @projectName digit-binhu-back
 * @date 2023/9/22 15:20
 */
@Slf4j
public abstract class AbstractTask implements ITask {
    @Autowired
    SquirrelRedis squirrelRedis;

    @Override
    public void run(String params,Long jobId) {
        String lockVal = IdUtil.fastUUID(), lockKey = "lock:job:" + jobId;
        try {
            boolean lock = squirrelRedis.lock(lockKey, lockVal);
            if (lock) {
                this.handler(params);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            //  squirrelRedis.unlock(lockKey, lockVal);
        }
    }
}
