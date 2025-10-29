package com.znv.manage.service.information;

import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.information.CommandInformationTemplate;

public interface CommandInformationTemplateService {
    Result getTemplate(Long id, String title, String type1, String type2, Integer page, Integer pageSize);

    Result insertTemplate(CommandInformationTemplate commandInformationTemplate);

    Result updateTemplate(CommandInformationTemplate commandInformationTemplate);

    Result deleteTemplate(Long id);
}
