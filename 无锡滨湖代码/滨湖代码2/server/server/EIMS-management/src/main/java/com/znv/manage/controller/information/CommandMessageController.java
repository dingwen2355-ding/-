package com.znv.manage.controller.information;

import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.information.CommandInformationTemplate;
import com.znv.manage.common.bean.information.MessageRelease;
import com.znv.manage.service.information.CommandInformationTemplateService;
import com.znv.manage.service.information.CommandMessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 指挥调度-信息发布/信息模板
 *
 * @author ：0049003788
 * @date ：2022/5/10 10:17
 */
@RestController
@Api(tags = "指挥调度-信息发布/信息模板")
@RequestMapping("/commandMessage")
public class CommandMessageController {
    @Autowired
    CommandMessageService commandMessageService;

    @Autowired
    CommandInformationTemplateService commandInformationTemplateService;

    @ApiOperation("查询信息")
    @GetMapping("getMessage")
    public Result getMessage(@RequestParam(value = "title", required = false) String title,
                             @RequestParam(value = "type1", required = false) String type1,
                             @RequestParam(value = "type2", required = false) String type2,
                             @RequestParam(value = "startDate", required = false) String startDate,
                             @RequestParam(value = "endDate", required = false) String endDate) {
        return commandMessageService.getMessage(title, type1, type2, startDate, endDate);
    }

    @ApiOperation("修改信息")
    @PostMapping("updateMessage")
    public Result updateMessage(@RequestBody MessageRelease messageRelease) {
        return commandMessageService.updateMessage(messageRelease);
    }

    @ApiOperation("新增信息")
    @PostMapping("insertMessage")
    public Result insertMessage(@RequestBody MessageRelease messageRelease) {
        return commandMessageService.insertMessage(messageRelease);
    }

    @ApiOperation("删除信息")
    @GetMapping("deleteMessage")
    public Result deleteMessage(@RequestParam(value = "id") Long id) {
        return commandMessageService.deleteMessage(id);
    }

    @ApiOperation("查询信息模板")
    @GetMapping("getTemplate")
    public Result getTemplate(@RequestParam(value = "id", required = false) Long id,
                              @RequestParam(value = "title", required = false) String title,
                              @RequestParam(value = "type1", required = false) String type1,
                              @RequestParam(value = "type2", required = false) String type2,
                              @RequestParam(value = "page", required = false) Integer page,
                              @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return commandInformationTemplateService.getTemplate(id, title, type1, type2, page, pageSize);
    }

    @ApiOperation("新增信息模板")
    @PostMapping("insertTemplate")
    public Result insertTemplate(@RequestBody CommandInformationTemplate commandInformationTemplate) {
        return commandInformationTemplateService.insertTemplate(commandInformationTemplate);
    }

    @ApiOperation("更新信息模板")
    @PostMapping("updateTemplate")
    public Result updateTemplate(@RequestBody CommandInformationTemplate commandInformationTemplate) {
        return commandInformationTemplateService.updateTemplate(commandInformationTemplate);
    }

    @ApiOperation("删除信息模板")
    @GetMapping("deleteTemplate")
    public Result deleteTemplate(@RequestParam(value = "id") Long id) {
        return commandInformationTemplateService.deleteTemplate(id);
    }
}
