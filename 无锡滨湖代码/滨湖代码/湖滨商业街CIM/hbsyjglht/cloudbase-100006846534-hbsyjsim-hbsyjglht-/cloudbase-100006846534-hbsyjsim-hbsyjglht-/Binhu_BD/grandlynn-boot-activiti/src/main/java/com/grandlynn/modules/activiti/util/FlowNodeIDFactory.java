package com.grandlynn.modules.activiti.util;

import cn.hutool.core.util.IdUtil;

/**
 * 流程定义节点id生成器
 */
public class FlowNodeIDFactory {
    // 基于雪花算法的id
    public static String randomSequenceFlowId() {
//        return IDFactory.snowflakeId();
        return IdUtil.createSnowflake(1, 1).nextIdStr();
    }
}