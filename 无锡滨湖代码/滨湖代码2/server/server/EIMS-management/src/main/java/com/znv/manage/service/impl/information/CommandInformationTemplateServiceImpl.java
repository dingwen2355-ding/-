package com.znv.manage.service.impl.information;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.information.CommandInformationTemplate;
import com.znv.manage.common.exception.ResultCodeEnum;
import com.znv.manage.dao.information.CommandInformationTemplateMapper;
import com.znv.manage.service.information.CommandInformationTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class CommandInformationTemplateServiceImpl implements CommandInformationTemplateService {

    @Autowired
    CommandInformationTemplateMapper commandInformationTemplateMapper;

    @Override
    public Result getTemplate(Long id, String title, String type1, String type2, Integer page, Integer pageSize) {
        Result result = new Result();
        try {
            if (page != null && pageSize != null) {
                PageHelper.startPage(page, pageSize);
            }
            result.setData(new PageInfo<>(commandInformationTemplateMapper.getTemplate(id, title, type1, type2)));
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result insertTemplate(CommandInformationTemplate commandInformationTemplate) {
        Result result = new Result();
        try {
            commandInformationTemplateMapper.insert(commandInformationTemplate);
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result updateTemplate(CommandInformationTemplate commandInformationTemplate) {
        Result result = new Result();
        try {
            commandInformationTemplateMapper.updateByPrimaryKey(commandInformationTemplate);
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result deleteTemplate(Long id) {
        Result result = new Result();
        try {
            commandInformationTemplateMapper.deleteByPrimaryKey(id);
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }
        return result;
    }
}
