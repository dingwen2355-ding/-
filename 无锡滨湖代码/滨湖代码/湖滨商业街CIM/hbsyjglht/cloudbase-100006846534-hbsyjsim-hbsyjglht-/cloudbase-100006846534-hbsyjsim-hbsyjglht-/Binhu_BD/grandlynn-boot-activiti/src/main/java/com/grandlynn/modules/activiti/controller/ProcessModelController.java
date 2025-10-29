package com.grandlynn.modules.activiti.controller;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.grandlynn.common.api.vo.Result;
import com.grandlynn.common.aspect.annotation.AutoLog;
import com.grandlynn.common.constant.CommonConstant;
import com.grandlynn.modules.activiti.domain.vo.ProcessModelVO;
import com.grandlynn.modules.activiti.service.IProcessDefinitionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ModelQuery;
import org.activiti.validation.ProcessValidator;
import org.activiti.validation.ProcessValidatorFactory;
import org.activiti.validation.ValidationError;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/processModel")
@Api(tags = "流程模型")
@Slf4j
public class ProcessModelController {

    @Autowired
    private IProcessDefinitionService processDefinitionService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/save")
    @ApiOperation(value = "保存流程模型", notes = "保存流程模型")
    public Result saveProcess(@RequestBody ProcessModelVO processModel) {
        try {

            Model model = null;
            if (StringUtils.isBlank(processModel.getProcessDefinitionId()) || "0".equals(processModel.getProcessDefinitionId())) {
                //初始化一个空模型
                model = repositoryService.newModel();
            } else {
                model = repositoryService.getModel(processModel.getProcessDefinitionId());
            }
            //设置一些默认信息
            int revision = 1;
            String name = processModel.getProcessName();
            String description = processModel.getProcessName();
            String key = processModel.getProcesskey();
            String xml = processModel.getProcessDescriptor();
            if (StrUtil.isBlank(name)) {
                name = "new-process";
            }
            if (StrUtil.isBlank(description)) {
                description = "description";
            }
            if (StrUtil.isBlank(key)) {
                key = "processKey";
            }


            ObjectNode modelNode = objectMapper.createObjectNode();
            modelNode.put("name", name);
            modelNode.put("description", description);
            modelNode.put("revision", revision);

            model.setName(name);
            model.setKey(key);
            model.setMetaInfo(modelNode.toString());

            repositoryService.saveModel(model);
            String id = model.getId();

//            //完善ModelEditorSource
//            ObjectNode editorNode = objectMapper.createObjectNode();
//            editorNode.put("id", "canvas");
//            editorNode.put("resourceId", "canvas");
//            ObjectNode stencilSetNode = objectMapper.createObjectNode();
//            stencilSetNode.put("namespace",
//                    "http://b3mn.org/stencilset/bpmn2.0#");
//            editorNode.put("stencilset", stencilSetNode);
            repositoryService.addModelEditorSource(id, xml.getBytes("utf-8"));
            return Result.ok("模型创建成功！");
        } catch (IOException e) {
            e.printStackTrace();
            log.info("模型创建失败！");
            return Result.ok("模型创建失败！");
        }

    }

    /**
     * 获取流程模型列表
     *
     * @param request
     * @return
     */
    @GetMapping(value = "/list")
    @ApiOperation(value = "获取流程模型列表", notes = "获取流程模型列表")
    public Result list(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest request) {
        ModelQuery modelQuery = repositoryService.createModelQuery();
        String keyWord = request.getParameter("keyWord");//搜索关键字
        if (StrUtil.isNotBlank(keyWord)) {
            modelQuery.modelNameLike("%" + keyWord + "%");
        }
        List<Model> models = modelQuery.orderByCreateTime().desc().listPage((pageNo - 1) * pageSize, pageSize);
        return Result.ok(models);

    }


    @DeleteMapping("/delete")
    @ApiOperation(value = "删除流程模型", notes = "删除流程模型")
    public Result deleteModel(String modelId) {
        try {

            repositoryService.deleteModel(modelId);
            return Result.ok("删除成功！");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("删除失败！modelId：" + modelId);
            return Result.error("删除失败！");
        }

    }


    /**
     * 获取流程模型XML
     *
     * @return
     */
    @AutoLog(value = "获取流程XML")
    @ApiOperation(value = "获取流程模型XML", notes = "获取流程模型XML")
    @GetMapping(value = "/getXml")
    public Result<?> getProcessXml(String modelId) {
        String xml;
        try {
            xml = new String(repositoryService.getModelEditorSource(modelId), "utf-8");
            Result result = new Result();
            result.setSuccess(true);
            result.setCode(CommonConstant.SC_OK_200);
            result.setMessage("成功");
            result.setResult(xml);
            return result;
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage());
            return Result.error("获取xml失败");
        }
    }

    @PutMapping("/deployment")
    @ApiOperation(value = "发布流程模型", notes = "发布流程模型")
    public Result deploy(@RequestBody Map params) {
        String id = (String) params.get("id");
        // 获取模型
        Model modelData = repositoryService.getModel(id);
        byte[] bytes = repositoryService.getModelEditorSource(modelData.getId());

        if (bytes == null) {
            return Result.error("模型数据为空，请先成功设计流程并保存");
        }

        try {
            InputStreamReader in = new InputStreamReader(new ByteArrayInputStream(bytes), "UTF-8");
            XMLStreamReader xtr = XMLInputFactory.newInstance().createXMLStreamReader(in);
            BpmnModel bpmnModel = new BpmnXMLConverter().convertToBpmnModel(xtr);

            //验证bpmnModel 是否是正确的bpmn xml文件
            ProcessValidatorFactory processValidatorFactory = new ProcessValidatorFactory();

            ProcessValidator defaultProcessValidator = processValidatorFactory.createDefaultProcessValidator();
            //验证失败信息的封装ValidationError

            List<ValidationError> validate = defaultProcessValidator.validate(bpmnModel);

            if (validate.size() > 0) {
//                return Result.error("模型不符要求，请至少设计一条主线流程");
                return Result.error(validate.get(0).getProblem());
            }
//            byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(model);

            // 部署发布模型流程
            String processName = modelData.getName() + ".bpmn20.xml";
            Deployment deployment = repositoryService.createDeployment()
                    .name(modelData.getName())
                    .addString(processName, new String(bytes, "UTF-8"))
                    .deploy();
        } catch (Exception e) {
            log.error(e.getMessage(), e);

            return Result.error("部署失败！");
        }

        return Result.ok("部署成功");
    }

}
