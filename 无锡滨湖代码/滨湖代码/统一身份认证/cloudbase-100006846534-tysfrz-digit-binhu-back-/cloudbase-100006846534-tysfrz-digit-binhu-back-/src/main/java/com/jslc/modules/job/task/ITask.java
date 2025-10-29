package com.jslc.modules.job.task;

/**
 * @author zhengyongbiao
 * @title: ITask
 * @projectName digit-binhu-back
 * @description: 定时任务接口，所有定时任务都要实现该接口
 * @date 2023/9/13 11:30
 */
public interface ITask {
    /**
     * 执行定时任务接口
     *
     * @param params 参数，多参数使用JSON数据
     */
    void run(String params,Long jobId);

    /**
     * @描述 业务逻辑实现
     * @创建人 zhengyongbiao
     * @创建时间 2023/9/22 15:54
     * @返回值 {@link null}
     */
    void handler(String params);
}
