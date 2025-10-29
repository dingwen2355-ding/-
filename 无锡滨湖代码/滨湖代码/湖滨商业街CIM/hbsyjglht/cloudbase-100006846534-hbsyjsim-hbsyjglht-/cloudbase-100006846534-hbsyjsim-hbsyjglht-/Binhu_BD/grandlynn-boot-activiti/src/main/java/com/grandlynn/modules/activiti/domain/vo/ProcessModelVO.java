package com.grandlynn.modules.activiti.domain.vo;

import lombok.Data;


@Data
public class ProcessModelVO {
    private String processkey;
    private String processName;
    private String processDescriptor;
    private String processDefinitionId;
}
