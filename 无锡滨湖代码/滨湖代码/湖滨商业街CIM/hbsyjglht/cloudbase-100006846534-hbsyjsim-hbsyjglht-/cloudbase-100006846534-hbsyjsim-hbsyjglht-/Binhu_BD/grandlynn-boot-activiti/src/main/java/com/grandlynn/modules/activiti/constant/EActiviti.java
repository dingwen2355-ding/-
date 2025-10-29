package com.grandlynn.modules.activiti.constant;

/**
 * activiti的内置变量和属性名称
 */
public interface EActiviti {

    // 内置变量
    // 多实例执行变量_ACTIVIT_NULL_ASSIGNEE
    String NUMBER_OF_INSTANCES = "nrOfInstances";
    String NUMBER_OF_ACTIVE_INSTANCES = "nrOfActiveInstances";
    String NUMBER_OF_COMPLETED_INSTANCES = "nrOfCompletedInstances";
    // 多实例分支执行变量
    String LOOP_COUNTER = "loopCounter";

    // 内置属性
    String BUSINESS_KEY = "businessKey";
    String VARIABLES = "variables";
    String INITIATOR = "initiator";

    // 候选用户组前缀
    String CANDIDATE_GROUPS_PREFIX = "_GROUPS:";
    // 候选用户前缀
    String CANDIDATE_USERS_PREFIX = "_USERS:";
    // 节点空执行人
    String ACTIVITI_NULL_ASSIGNEE = "_ACTIVIT_NULL_ASSIGNEE";
    // 自动通过机器人
    String ACTIVITI_AUTO_PASS_ROBOT = "_ACTIVITI_AUTO_PASS_ROBOT";
    // 自动拒绝机器人
    String ACTIVITI_AUTO_REFUSE_ROBOT = "_ACTIVITI_AUTO_REFUSE_ROBOT";
    // 节点自动skip表达式
    String ACTIVITI_SKIP_EXPRESSION = "_ACTIVITI_SKIP_EXPRESSION_ENABLED";
}
