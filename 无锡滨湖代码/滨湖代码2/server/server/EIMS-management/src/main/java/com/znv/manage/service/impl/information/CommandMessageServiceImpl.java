package com.znv.manage.service.impl.information;

import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.information.MessageRelease;
import com.znv.manage.common.exception.ResultCodeEnum;
import com.znv.manage.dao.information.MessageReleaseMapper;
import com.znv.manage.service.information.CommandMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class CommandMessageServiceImpl implements CommandMessageService {
    @Autowired
    MessageReleaseMapper messageReleaseMapper;


    @Override
    public Result getMessage(String title, String type1, String type2, String startDate, String endDate) {
        Result result = new Result();
        try {
            result.setData(messageReleaseMapper.getMessage(title, type1, type2, startDate, endDate));
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result updateMessage(MessageRelease messageRelease) {
        Result result = new Result();
        try {
            messageReleaseMapper.updateByPrimaryKey(messageRelease);
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result insertMessage(MessageRelease messageRelease) {
        Result result = new Result();
        try {
            messageReleaseMapper.insert(messageRelease);
            List<String> list = new ArrayList<>();
            list.add(messageRelease.getId().toString());
            result.setData(messageReleaseMapper.getMessages(list));
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result deleteMessage(Long id) {
        Result result = new Result();
        try {
            messageReleaseMapper.deleteByPrimaryKey(id);
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }
        return result;
    }
}
