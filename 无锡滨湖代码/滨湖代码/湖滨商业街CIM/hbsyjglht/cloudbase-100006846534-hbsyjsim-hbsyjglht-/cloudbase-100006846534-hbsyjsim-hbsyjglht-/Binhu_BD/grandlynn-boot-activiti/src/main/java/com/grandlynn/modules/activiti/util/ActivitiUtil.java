package com.grandlynn.modules.activiti.util;

import com.grandlynn.common.exception.GrandlynnBootException;
import com.grandlynn.common.util.SpringContextUtils;
import org.activiti.bpmn.model.*;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.util.CollectionUtil;
import org.activiti.engine.impl.util.ProcessDefinitionUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 用于获取流程引擎
 */
public class ActivitiUtil {

    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    private static ActivitiUtil activitiUtil;

    RepositoryService repositoryService = processEngine.getRepositoryService();
    TaskService taskService = processEngine.getTaskService();
    RuntimeService runtimeService = processEngine.getRuntimeService();
    HistoryService historyService = processEngine.getHistoryService();

    public static ActivitiUtil instance() {
        if (null == activitiUtil) {
            activitiUtil = new ActivitiUtil();
        }
        return activitiUtil;
    }

    public RepositoryService getRepositoryService() {
        return repositoryService;
    }

    public void setRepositoryService(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    public TaskService getTaskService() {
        return taskService;
    }

    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

    public RuntimeService getRuntimeService() {
        return runtimeService;
    }

    public void setRuntimeService(RuntimeService runtimeService) {
        this.runtimeService = runtimeService;
    }

    public HistoryService getHistoryService() {
        return historyService;
    }

    public void setHistoryService(HistoryService historyService) {
        this.historyService = historyService;
    }


    /**
     * 基于流程定义查询任务上一个任务节点
     *
     * @param userTask
     * @return
     */
    public static List<UserTask> getLastUserTaskBaseProcessDefinition(FlowNode userTask) {
        if (Objects.isNull(userTask)) {
            return null;
        }

        List<SequenceFlow> sequenceFlows = userTask.getIncomingFlows();
        List<UserTask> lastUserTasks = new ArrayList<UserTask>();
        if (CollectionUtil.isNotEmpty(sequenceFlows)) {
            for (SequenceFlow sequenceFlow : sequenceFlows) {
                FlowNode flowNode = (FlowNode) sequenceFlow.getSourceFlowElement();
                if (flowNode instanceof UserTask) {
                    lastUserTasks.add((UserTask) flowNode);
                } else {
                    lastUserTasks.addAll(getLastUserTaskBaseProcessDefinition(flowNode));
                }
            }
        }

        return lastUserTasks;
    }

    /**
     * 基于流程定义和历史任务获取当前节点的上一个任务节点
     *
     * @param taskEntity
     * @param userTask
     * @return
     */
    public static UserTask getLastUserTask(TaskEntity taskEntity, FlowNode userTask) {
        UserTask lastUserTask = null;
        List<UserTask> lastUserTasks = getLastUserTaskBaseProcessDefinition(userTask);
        BpmnModel bpmnModel = ProcessDefinitionUtil.getBpmnModel(taskEntity.getProcessDefinitionId());
        if (CollectionUtil.isNotEmpty(lastUserTasks)) {
            if (lastUserTasks.size() == 1) {
                lastUserTask = lastUserTasks.get(0);
            } else {// 多分支流入情况处理
                List<String> lastUserTaskDefinitionKeys = lastUserTasks.stream().map(UserTask::getId)
                        .collect(Collectors.toList());

                // 查询上一个完成任务的节点
                String executionId = taskEntity.getExecutionId();
                List<HistoricTaskInstance> historicTaskInstances = SpringContextUtils.getBean(HistoryService.class)//
                        .createHistoricTaskInstanceQuery()//
                        .executionId(executionId)//
                        .finished()//
                        .orderByTaskCreateTime().desc()//
                        .list();

                if (CollectionUtil.isNotEmpty(historicTaskInstances)) {
                    HistoricTaskInstance historicTaskInstance = historicTaskInstances.stream()
                            .filter(item -> lastUserTaskDefinitionKeys.contains(item.getTaskDefinitionKey()))
                            .findFirst().orElse(null);
                    if (Objects.nonNull(historicTaskInstance)) {
                        String lastActivityId = historicTaskInstance.getTaskDefinitionKey();
                        lastUserTask = (UserTask) bpmnModel.getFlowElement(lastActivityId);
                    }
                }
            }
        }

        if (Objects.isNull(lastUserTask)) {
            throw new GrandlynnBootException("Last task is null");
        }
        return lastUserTask;
    }

    /**
     * 基于流程定义检查当前节点流入是否是并行网关, 包含网关
     *
     * @param userTask
     * @return
     */
    public static boolean checkCurrentFlowNodeIsParallelIncomingBaseProcessDefinition(FlowNode userTask) {
        if (Objects.nonNull(userTask)) {
            List<SequenceFlow> sequenceFlows = userTask.getIncomingFlows();
            if (CollectionUtil.isNotEmpty(sequenceFlows)) {
                for (SequenceFlow sequenceFlow : sequenceFlows) {
                    FlowNode flowNode = (FlowNode) sequenceFlow.getSourceFlowElement();
                    if (flowNode instanceof InclusiveGateway || flowNode instanceof ParallelGateway) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}