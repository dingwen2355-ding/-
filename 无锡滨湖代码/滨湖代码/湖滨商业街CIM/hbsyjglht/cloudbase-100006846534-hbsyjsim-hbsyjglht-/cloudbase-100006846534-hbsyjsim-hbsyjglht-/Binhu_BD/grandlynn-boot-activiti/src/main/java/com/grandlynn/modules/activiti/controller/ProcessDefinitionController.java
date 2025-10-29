package com.grandlynn.modules.activiti.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.grandlynn.common.api.vo.Result;
import com.grandlynn.common.aspect.annotation.AutoLog;
import com.grandlynn.modules.activiti.domain.vo.ProcessDefVO;
import com.grandlynn.modules.activiti.service.IProcessDefinitionService;
import com.grandlynn.modules.activiti.service.ProcessImageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;


@RestController
@RequestMapping("/processDefinition")
@Api(tags = "流程模型定义")
public class ProcessDefinitionController {

    @Autowired
    private IProcessDefinitionService processDefinitionService;

    @Autowired
    private ProcessImageService processImageService;


    /**
     * 获取流程定义集合
     *
     * @param processDefVO
     * @return
     */
    @GetMapping(value = "/list")
    public Result<IPage<ProcessDefVO>> list(ProcessDefVO processDefVO, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        Result<IPage<ProcessDefVO>> result = new Result<>();
        Page<ProcessDefVO> pageList = new Page<>(pageNo, pageSize);
        pageList = processDefinitionService.selectProcessDefinitionList(pageList, processDefVO);
        result.setResult(pageList);
        result.setSuccess(true);
        return result;

    }

    /**
     * 获取流程图
     *
     * @param processId
     * @param response
     */
    @AutoLog(value = "流程实例-获取流程图")
    @ApiOperation(value = "流程实例-获取流程图",
            notes = "流程实例-获取流程图")
    @GetMapping("getImg/{processId}")
    public void getImg(@PathVariable String processId, HttpServletResponse response) {
        try {
            InputStream imgIs = processImageService.getFlowImgByProcInstId(processId);
            byte[] bytes = IOUtils.toByteArray(imgIs);
            //设置响应类型
            response.setContentType("image/svg+xml");
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(bytes);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取流程图
     *
     * @param processDefId
     * @param response
     */
    @GetMapping("getProcessDefImg/{processDefId}")
    public void getProcessDefImg(@PathVariable String processDefId, HttpServletResponse response) {
        try {
            InputStream imgIs = processImageService.getFlowImgByProcessDefId(processDefId);
            byte[] bytes = IOUtils.toByteArray(imgIs);
            //设置响应类型
            response.setContentType("image/svg+xml");
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(bytes);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 启动挂起流程流程定义
     *
     * @param processDefinition
     * @return
     */
    @PostMapping("/suspendOrActivate")
    @ResponseBody
    public Result suspendOrActivate(@RequestBody ProcessDefVO processDefinition) {
        processDefinitionService.suspendOrActivate(processDefinition.getId(), processDefinition.getSuspensionState());
        return Result.ok();
    }


    /**
     * 删除流程定义
     *
     * @param deploymentId
     * @return
     */
//    @Log(title = "流程定义管理", businessType = BusinessType.DELETE)
    @DeleteMapping(value = "/remove/{deploymentId}")
    public Result delDefinition(@PathVariable("deploymentId") String deploymentId) {
        return Result.ok(processDefinitionService.deleteProcessDefinitionById(deploymentId));
    }


}
