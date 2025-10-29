package com.grandlynn.modules.activiti.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.grandlynn.modules.activiti.domain.vo.ProcessDefVO;
import com.grandlynn.modules.activiti.service.IProcessDefinitionService;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipInputStream;


@Service
@Slf4j
public class ProcessDefinitionServiceImpl implements IProcessDefinitionService {
    @Autowired
    private RepositoryService repositoryService;

    //    @Autowired
//    private ActReDeploymentMapper actReDeploymentMapper;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private RuntimeService runtimeService;

    @Override
    public Page<ProcessDefVO> selectProcessDefinitionList(Page<ProcessDefVO> page, ProcessDefVO processDefVO) {
//        Page<ProcessDefinitionDTO> list = new Page<>();
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery().processDefinitionKey(processDefVO.getKey()).orderByProcessDefinitionId().orderByProcessDefinitionVersion().desc();
//        if (StringUtils.isNotBlank(processDefinition.getName())) {
//            processDefinitionQuery.processDefinitionNameLike("%" + processDefinition.getName() + "%");
//        }
//        if (StringUtils.isNotBlank(processDefinition.getKey())) {
//            processDefinitionQuery.processDefinitionKeyLike("%" + processDefinition.getKey() + "%");
//        }
        List<ProcessDefinition> processDefinitions = processDefinitionQuery.listPage(((int) page.getCurrent() - 1) * (int) page.getSize(), (int) page.getSize());
        long count = processDefinitionQuery.count();
        page.setTotal(count);
        if (count != 0) {
            List<Deployment> deploymentList = repositoryService.createDeploymentQuery().deploymentId(processDefVO.getKey()).list();
            List<ProcessDefVO> voList = processDefinitions.stream()
                    .map(pd ->
                            new ProcessDefVO((ProcessDefinitionEntityImpl) pd, repositoryService.createDeploymentQuery().deploymentId(pd.getDeploymentId()).singleResult()))
                    .collect(Collectors.toList());
            page.setRecords(voList);
        }

        return page;
    }


    @Override
    public int deleteProcessDefinitionById(String id) {
        repositoryService.deleteDeployment(id, false);
        return 1;
    }

    @Override
    public void uploadStreamAndDeployment(MultipartFile file) throws IOException {
        // 获取上传的文件名
        String fileName = file.getOriginalFilename();
        // 得到输入流（字节流）对象
        InputStream fileInputStream = file.getInputStream();
        // 文件的扩展名
        String extension = FilenameUtils.getExtension(fileName);

        if (extension.equals("zip")) {
            ZipInputStream zip = new ZipInputStream(fileInputStream);
            repositoryService.createDeployment()//初始化流程
                    .addZipInputStream(zip)
                    .deploy();
        } else {
            repositoryService.createDeployment()//初始化流程
                    .addInputStream(fileName, fileInputStream)
                    .deploy();
        }
    }

    @Override
    public void suspendOrActivate(String id, Integer suspendState) {
        if (1 == suspendState) {
            // 当流程定义被挂起时，已经发起的该流程定义的流程实例不受影响（如果选择级联挂起则流程实例也会被挂起）。
            // 当流程定义被挂起时，无法发起新的该流程定义的流程实例。
            // 直观变化：act_re_procdef 的 SUSPENSION_STATE_ 为 2
            repositoryService.suspendProcessDefinitionById(id);
        } else if (2 == suspendState) {
            repositoryService.activateProcessDefinitionById(id);
        }
    }

    @Override
    public String upload(MultipartFile multipartFile) throws IOException {
//        return FileUploadUtils.upload(RuoYiConfig.getUploadPath() + "/processDefinition", multipartFile);
        return null;
    }


}
