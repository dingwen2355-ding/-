package com.grandlynn.modules.activiti.service;

import com.grandlynn.modules.activiti.constant.EActiviti;
import com.grandlynn.modules.activiti.util.ActivitiUtil;
import com.grandlynn.modules.activiti.util.FlowNodeIDFactory;
import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.MultiInstanceLoopCharacteristics;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityManager;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntityManager;
import org.activiti.engine.impl.util.CollectionUtil;
import org.activiti.engine.impl.util.ProcessDefinitionUtil;
import org.activiti.engine.task.DelegationState;
import org.activiti.engine.task.Task;

import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;

/**
 * 回退到上一个任务节点命令 <br>
 * <b>上个节点是并行网关时, 不能回退</b>
 */
@Slf4j
public class BackOneStepCmd implements Command<Void>, Serializable {

    private static final long serialVersionUID = 1L;

    // 当前任务
    private Task task;
    private String backReason;

    public BackOneStepCmd(Task task) {
        this.task = Objects.requireNonNull(task, "Task can not be null");
    }

    public BackOneStepCmd(Task task, String deleteReason) {
        this(task);
        this.backReason = deleteReason;
    }

    @Override
    public Void execute(CommandContext commandContext) {
        // 查询BpmnModel
        String processDefinitionId = task.getProcessDefinitionId();
        BpmnModel bpmnModel = ProcessDefinitionUtil.getBpmnModel(processDefinitionId);

        // 当前节点
        String activityId = task.getTaskDefinitionKey();
        UserTask userTask = (UserTask) bpmnModel.getFlowElement(activityId);

        // 检测是否可以回退
        // 当前节点流入是并行, 包含网关时禁止回退
        if (ActivitiUtil.checkCurrentFlowNodeIsParallelIncomingBaseProcessDefinition(userTask)) {
            throw new RuntimeException("Current node is parallel gateway, can not execute back cmd");
        }

        // 查询上一个UserTask
        UserTask lastUserTask = ActivitiUtil.getLastUserTask((TaskEntity) task, userTask);
        log.info("back one step to {}", lastUserTask.getId());

        // 建立回退方向
        SequenceFlow backStepSequenceFlow = new SequenceFlow();
        backStepSequenceFlow.setId(FlowNodeIDFactory.randomSequenceFlowId());
        backStepSequenceFlow.setSourceFlowElement(userTask);
        backStepSequenceFlow.setTargetFlowElement(lastUserTask);

        // 并发问题
        // 记录当前节点的原活动方向
        // List<SequenceFlow> originalOutgoingFlows =
        // ListUtil.toList(currentUserTask.getOutgoingFlows());
        // 设置新的流程走向
        // currentUserTask.setOutgoingFlows(ListUtil.toList(backStepSequenceFlow));
        // 完成任务
        // taskService.complete(task.getId());
        // 还原源活动方向
        // currentUserTask.setOutgoingFlows(originalOutgoingFlows);

        // 解决并发流向出错问题
        // 查询任务, 删除的任务不需要触发任何事件
        TaskEntityManager taskEntityManager = commandContext.getTaskEntityManager();
        TaskEntity taskEntity = taskEntityManager.findById(task.getId());

        // 挂起的任务需要先激活
        if (taskEntity.getDelegationState() != null
                && taskEntity.getDelegationState().equals(DelegationState.PENDING)) {
            throw new ActivitiException("A delegated task cannot be completed, but should be resolved instead.");
        }

        // 完成当前任务
        taskEntityManager.deleteTask(taskEntity, backReason, false, false);
        ExecutionEntityManager executionEntityManager = commandContext.getExecutionEntityManager();
        ExecutionEntity executionEntity = executionEntityManager.findById(taskEntity.getExecutionId());

        // 检测当前任务节点类型
        MultiInstanceLoopCharacteristics multiInstanceLoopCharacteristics = userTask.getLoopCharacteristics();
        if (multiInstanceLoopCharacteristics != null) {
            if (multiInstanceLoopCharacteristics.isSequential()) {
                // 序签提前结束
                leaveSequentialMultiInstance(executionEntity, backStepSequenceFlow);
            } else {
                // 会签或签提前结束
                leaveParallelMultiInstance(executionEntity, backStepSequenceFlow);
            }
        } else {
            // 一般任务节点
            leaveUserTask(executionEntity, backStepSequenceFlow);
        }
        return null;
    }

    /**
     * 当前节点流转
     *
     * @param executionEntity
     * @param backStepSequenceFlow
     */
    protected void leaveUserTask(ExecutionEntity executionEntity, SequenceFlow backStepSequenceFlow) {
        executionEntity.setCurrentFlowElement(backStepSequenceFlow);
        Context.getAgenda().planTakeOutgoingSequenceFlowsOperation(executionEntity, true);
    }

    /**
     * 平行节点流转
     *
     * @param childExecution
     * @param backStepSequenceFlow
     */
    protected void leaveParallelMultiInstance(ExecutionEntity childExecution, SequenceFlow backStepSequenceFlow) {
        Context.getCommandContext().getHistoryManager().recordActivityEnd(childExecution, null);
        ExecutionEntity multiInstanceRootExecution = getMultiInstanceRootExecution(childExecution);

        // 清除变量和子执行线
        childExecution.removeVariableLocal(EActiviti.LOOP_COUNTER);
        multiInstanceRootExecution.removeVariableLocal(EActiviti.NUMBER_OF_COMPLETED_INSTANCES);
        multiInstanceRootExecution.removeVariableLocal(EActiviti.NUMBER_OF_ACTIVE_INSTANCES);
        multiInstanceRootExecution.removeVariableLocal(EActiviti.NUMBER_OF_INSTANCES);
        this.deleteChildExecutions((ExecutionEntity) multiInstanceRootExecution, false, Context.getCommandContext());

        // 流转到新的节点
        multiInstanceRootExecution.setScope(false);
        multiInstanceRootExecution.setMultiInstanceRoot(false);
        multiInstanceRootExecution.setActive(true);
        this.leaveUserTask(multiInstanceRootExecution, backStepSequenceFlow);
    }

    /**
     * 序签节点流转
     *
     * @param childExecution
     * @param backStepSequenceFlow
     */
    protected void leaveSequentialMultiInstance(ExecutionEntity childExecution, SequenceFlow backStepSequenceFlow) {
        Context.getCommandContext().getHistoryManager().recordActivityEnd(childExecution, null);
        ExecutionEntity multiInstanceRootExecution = getMultiInstanceRootExecution(childExecution);

        // 清除变量和子执行线
        childExecution.removeVariableLocal(EActiviti.LOOP_COUNTER);
        multiInstanceRootExecution.removeVariableLocal(EActiviti.NUMBER_OF_COMPLETED_INSTANCES);
        multiInstanceRootExecution.removeVariableLocal(EActiviti.NUMBER_OF_ACTIVE_INSTANCES);
        multiInstanceRootExecution.removeVariableLocal(EActiviti.NUMBER_OF_INSTANCES);
        Context.getCommandContext().getExecutionEntityManager()
                .deleteChildExecutions((ExecutionEntity) multiInstanceRootExecution, "MI_END", false);

        // 流转到新的节点
        multiInstanceRootExecution.setScope(false);
        multiInstanceRootExecution.setMultiInstanceRoot(false);
        multiInstanceRootExecution.setActive(true);
        this.leaveUserTask(multiInstanceRootExecution, backStepSequenceFlow);
    }

    // TODO: can the ExecutionManager.deleteChildExecution not be used?
    protected void deleteChildExecutions(ExecutionEntity parentExecution, boolean deleteExecution,
                                         CommandContext commandContext) {
        // Delete all child executions
        ExecutionEntityManager executionEntityManager = commandContext.getExecutionEntityManager();
        Collection<ExecutionEntity> childExecutions = executionEntityManager
                .findChildExecutionsByParentExecutionId(parentExecution.getId());
        if (CollectionUtil.isNotEmpty(childExecutions)) {
            for (ExecutionEntity childExecution : childExecutions) {
                deleteChildExecutions(childExecution, true, commandContext);
            }
        }

        if (deleteExecution) {
            executionEntityManager.deleteExecutionAndRelatedData(parentExecution, null, false);
        }
    }

    protected ExecutionEntity getMultiInstanceRootExecution(ExecutionEntity executionEntity) {
        ExecutionEntity multiInstanceRootExecution = null;
        ExecutionEntity currentExecution = executionEntity;
        while (currentExecution != null && multiInstanceRootExecution == null && currentExecution.getParent() != null) {
            if (currentExecution.isMultiInstanceRoot()) {
                multiInstanceRootExecution = currentExecution;
            } else {
                currentExecution = currentExecution.getParent();
            }
        }
        return multiInstanceRootExecution;
    }

}
