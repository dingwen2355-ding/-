package com.znv.manage.service;

import com.alibaba.fastjson.JSONObject;
import com.znv.manage.common.bean.InstructionImplement;
import com.znv.manage.common.bean.LeaderInstruction;
import com.znv.manage.common.bean.Result;

public interface InstructionService {
    Result getLeaderInstructionInplemention(String instructionId);

    Result getLeaderInstruction(String eventId);

    Result getLeaderInstructionInplemention2(String instructionId);

    Result insertLeaderInstruction(LeaderInstruction leaderInstruction);

    Result updateLeaderInstruction(LeaderInstruction leaderInstruction);

    Result getLeaderInstructionAndImplemention(String eventId, String status);

    Result insertInstructionImplemention(InstructionImplement instructionImplement);

    Result updateInstructionImplemention(InstructionImplement instructionImplement);

    Result updateInstructionImplement(JSONObject jsonObject);

    Result updateLeaderInstructionImplementNew(JSONObject json);

    Result updateLeaderInstructionNew(JSONObject json);

    void deleteInstructionImplement(String id);
}
