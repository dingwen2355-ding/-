package com.znv.manage.dao;

import com.znv.manage.common.bean.InstructionImplement;
import com.znv.manage.common.bean.LeaderInstruction;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface InstructionMapper {
    List<Map<String, Object>> getLeaderInstructionInplemention(@Param("eventId") String eventId,
                                                               @Param("status") String status);

    List<Map<String, Object>> getLeaderInstruction(@Param("eventId") String eventId,
                                                   @Param("status") String status);

    List<Map<String, Object>> getLeaderInstructionInplemention2(@Param("instructionId") String instructionId);

    int deleteLeaderInstructionByEventId(@Param("eventId") String eventId);

    void deleteLeaderInstructionImplementByEventId(@Param("eventId") String eventId);

    int insertLeaderInstruction(LeaderInstruction record);

    int insertInstructionImplementionsNew(@Param("lists") List<InstructionImplement> lists);

    int insertLeaderInstructionNew(@Param("lists") List<LeaderInstruction> lists);

    int updateLeaderInstruction(LeaderInstruction record);

    int insertInstructionImplemention(InstructionImplement instructionImplement);

    int updateInstructionImplemention(InstructionImplement instructionImplement);

    void deleteInstructionImplement(@Param("id") String id);
}
