package com.znv.manage.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.znv.manage.common.bean.InstructionImplement;
import com.znv.manage.common.bean.LeaderInstruction;
import com.znv.manage.common.bean.Result;
import com.znv.manage.common.exception.ResultCodeEnum;
import com.znv.manage.common.utils.DateUtil;
import com.znv.manage.dao.InstructionMapper;
import com.znv.manage.service.InstructionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class InstructionServiceImpl implements InstructionService {

    private static int tailId = 1;

    @Autowired
    InstructionMapper instructionMapper;

    @Override
    public Result getLeaderInstructionInplemention(String instructionId) {
        Result result = new Result();
        try {
            result.setData(instructionMapper.getLeaderInstructionInplemention(instructionId, null));
            result.setCode(ResultCodeEnum.NORMAL.getCode());
            result.setMessage(ResultCodeEnum.NORMAL.getName());
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result getLeaderInstruction(String eventId) {
        Result result = new Result();
        try {
            result.setData(instructionMapper.getLeaderInstruction(eventId, null));
            result.setCode(ResultCodeEnum.NORMAL.getCode());
            result.setMessage(ResultCodeEnum.NORMAL.getName());
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result getLeaderInstructionInplemention2(String instructionId) {

        Result result = new Result();
        try {
            result.setData(instructionMapper.getLeaderInstructionInplemention2(instructionId));
            result.setCode(ResultCodeEnum.NORMAL.getCode());
            result.setMessage(ResultCodeEnum.NORMAL.getName());
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result insertLeaderInstruction(LeaderInstruction leaderInstruction) {

        Result result = new Result();
        try {
            // 为每个领导指示添加ID
            tailId++;
            leaderInstruction.setInstructionId(leaderInstruction.getEventId() + tailId);
            leaderInstruction.setRecordtime(DateUtil.getStringDate());
            int i = instructionMapper.insertLeaderInstruction(leaderInstruction);
            result.setData("共插入：" + i + "条数据");
            result.setCode(ResultCodeEnum.CREATEORUPDATESUCCESS.getCode());
            result.setMessage(ResultCodeEnum.CREATEORUPDATESUCCESS.getName());
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result updateLeaderInstruction(LeaderInstruction leaderInstruction) {
        Result result = new Result();
        try {
            int i = instructionMapper.updateLeaderInstruction(leaderInstruction);
            result.setData("共更新：" + i + "条数据");
            result.setCode(ResultCodeEnum.CREATEORUPDATESUCCESS.getCode());
            result.setMessage(ResultCodeEnum.CREATEORUPDATESUCCESS.getName());
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result getLeaderInstructionAndImplemention(String eventId, String status) {
        Result result = new Result();
        try {
            List<Map<String, Object>> instructionList = instructionMapper.getLeaderInstruction(eventId, status);
            List<Map<String, Object>> implementionList = instructionMapper.getLeaderInstructionInplemention(eventId, status);
            Map<String, Object> map = new HashMap<>();
            map.put("leaderInstruction", instructionList);
            map.put("implemention", implementionList);
            result.setCode(ResultCodeEnum.NORMAL.getCode());
            result.setMessage(ResultCodeEnum.NORMAL.getName());
            result.setData(map);
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }

        return result;
    }

    @Override
    public Result insertInstructionImplemention(InstructionImplement instructionImplement) {

        Result result = new Result();
        try {
            int i = instructionMapper.insertInstructionImplemention(instructionImplement);

            result.setCode(ResultCodeEnum.NORMAL.getCode());
            result.setMessage(ResultCodeEnum.NORMAL.getName());
            result.setData("已插入" + i + "条数据");
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result updateInstructionImplemention(InstructionImplement instructionImplement) {

        Result result = new Result();
        try {
            int i = instructionMapper.updateInstructionImplemention(instructionImplement);
            result.setData("共更新：" + i + "条数据");
            result.setCode(ResultCodeEnum.CREATEORUPDATESUCCESS.getCode());
            result.setMessage(ResultCodeEnum.CREATEORUPDATESUCCESS.getName());
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public synchronized Result updateInstructionImplement(JSONObject jsonObject) {
        Result result = new Result();
        String instructionId = null;
        try {
            if (jsonObject.containsKey("leaderInstruction")) {
                JSONArray jsonArray = jsonObject.getJSONArray("leaderInstruction");
                List<LeaderInstruction> instructionList = jsonArray.toJavaList(LeaderInstruction.class);
                for (LeaderInstruction leaderInstruction : instructionList) {
                    if (!StringUtils.isEmpty(leaderInstruction.getEventId())) {
                        if (StringUtils.isEmpty(leaderInstruction.getInstructionId())) {
                            // 根据事件ID+时间生成领导指示ID
                            leaderInstruction.setInstructionId(leaderInstruction.getEventId() + System.currentTimeMillis());
                            instructionMapper.insertLeaderInstruction(leaderInstruction);
                        } else {
                            instructionMapper.updateLeaderInstruction(leaderInstruction);
                        }
                    }
                    instructionId = leaderInstruction.getInstructionId();
                }
            }
            if (jsonObject.containsKey("implemention")) {
                List<InstructionImplement> instructionImplementList = jsonObject.getJSONArray("implemention").toJavaList(InstructionImplement.class);
                for (InstructionImplement implement : instructionImplementList) {
                    if (!StringUtils.isEmpty(implement.getEventId())){
                        if (StringUtils.isEmpty(implement.getInstructionId())) {
                            if (!StringUtils.isEmpty(instructionId)) {
                                implement.setInstructionId(instructionId);
                                instructionMapper.insertInstructionImplemention(implement);
                            }
                        } else {
                            instructionMapper.updateInstructionImplemention(implement);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
            result.setResutCode(ResultCodeEnum.SYSTEMERROR.getCode(), ResultCodeEnum.SYSTEMERROR.getName());
        }
        return result;
    }

    @Override
    public Result updateLeaderInstructionImplementNew(JSONObject json) {
        String eventId = json.getString("eventId");
        if(StringUtils.isNotBlank(eventId)){
            JSONArray lists = json.getJSONArray("lists");
            String isAdd = json.getString("isAdd");
            List<InstructionImplement> maps = lists.toJavaList(InstructionImplement.class);
            if("1".equals(isAdd)){
                instructionMapper.deleteLeaderInstructionImplementByEventId(eventId);
            }
            if(maps.size()>0){
                for (InstructionImplement map : maps) {
                    if(StringUtils.isBlank(map.getRecordtime())){
                        map.setRecordtime(null);
                    }
                }
                instructionMapper.insertInstructionImplementionsNew(maps);
            }
            return new Result();
        }else{
            return new Result(500,"未包含eventId");
        }

    }

    @Override
    public Result updateLeaderInstructionNew(JSONObject json) {
             String eventId = json.getString("eventId");
            if(StringUtils.isNotBlank(eventId)){
            JSONArray lists = json.getJSONArray("lists");
            String isAdd = json.getString("isAdd");
            List<LeaderInstruction> maps = lists.toJavaList(LeaderInstruction.class);
            if("1".equals(isAdd)){
                instructionMapper.deleteLeaderInstructionByEventId(eventId);
            }
            if(maps.size()>0){
                for (LeaderInstruction map : maps) {
                    if(StringUtils.isBlank(map.getRecordtime())){
                        map.setRecordtime(null);
                    }
                }
                instructionMapper.insertLeaderInstructionNew(maps);
            }
            return new Result();
        }else{
            return new Result(500,"未包含eventId");
        }
    }

    @Override
    public void deleteInstructionImplement(String id) {
        instructionMapper.deleteInstructionImplement(id);
    }


}
