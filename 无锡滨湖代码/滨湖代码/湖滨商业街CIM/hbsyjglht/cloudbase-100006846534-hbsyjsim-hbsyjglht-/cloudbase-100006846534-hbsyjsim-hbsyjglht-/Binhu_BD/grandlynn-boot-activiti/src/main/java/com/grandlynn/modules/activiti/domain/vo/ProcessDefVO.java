package com.grandlynn.modules.activiti.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntityImpl;
import org.activiti.engine.repository.Deployment;

import java.util.Date;

/**
 * 流程定义Vo
 */
@ApiModel("流程定义Vo")
@Data
public class ProcessDefVO {

    /**
     * 流程部署Id
     */
    private String id;

    /**
     * 流程定义的Key
     */
    private String key;
    /**
     * 流程定义名称
     */
    private String name;
    /**
     * 流程版本
     */
    private Integer version;
    /**
     * 当前流程状态 1：激活，1挂起
     */
    private Integer suspensionState;

    private String deploymentId;
    private String resourceName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date deploymentTime;

    public ProcessDefVO() {
    }

    public ProcessDefVO(ProcessDefinitionEntityImpl processDefinition, Deployment deployment) {
        this.id = processDefinition.getId();
        this.name = processDefinition.getName();
        this.key = processDefinition.getKey();
        this.version = processDefinition.getVersion();
        this.deploymentId = processDefinition.getDeploymentId();
        this.resourceName = processDefinition.getResourceName();
        this.deploymentTime = deployment.getDeploymentTime();
        this.suspensionState = processDefinition.getSuspensionState();
    }


}
