package com.znv.manage.common.bean;

import lombok.Data;

@Data
public class LeaderInstruction {
    private String id;

    private String eventId;

    private String instructionId;

    private String leaderName;

    private String leaderDuty;

    private String instructionContent;

    private String recordtime;

    private String status;
}