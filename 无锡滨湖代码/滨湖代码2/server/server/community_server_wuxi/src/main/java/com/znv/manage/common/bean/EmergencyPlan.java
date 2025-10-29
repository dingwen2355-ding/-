package com.znv.manage.common.bean;

import lombok.Data;

@Data
public class EmergencyPlan {
    private String id;

    private String emergencyPlanTitle;

    private String emergencyPlanAbstract;

    private String emergencyPlanFile;

    private String eventType;
}
