package com.grandlynn.modules.activiti.service.impl;

import com.grandlynn.modules.activiti.service.ProcessImageService;
import com.grandlynn.modules.activiti.util.ProcessImageManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;

/**
 * 流程图service实现类
 *
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ProcessImageServiceImpl implements ProcessImageService {

    ProcessImageManager processImageManager;

    @Autowired
    public ProcessImageServiceImpl(ProcessImageManager processImageManager) {
        this.processImageManager = processImageManager;
    }

    /**
     * 根据流程实例Id获取流程图
     *
     * @param procInstId 流程实例id
     * @return inputStream
     * @throws Exception exception
     */
    @Override
    public InputStream getFlowImgByProcInstId(String procInstId) throws Exception {
        return processImageManager.getFlowImgByProcInstId(procInstId);
    }

    @Override
    public InputStream getFlowImgByProcessDefId(String processDefId) throws Exception {
        return processImageManager.getFlowImgByProcessDefId(processDefId);
    }
}