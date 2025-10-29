package com.grandlynn.modules.activiti.service;

import java.io.InputStream;

/**
 * 流程图service
 *
 */
public interface ProcessImageService {

    /**
     * 根据流程实例Id获取流程图
     *
     * @param procInstId 流程实例id
     * @return inputStream
     * @throws Exception exception
     */
    InputStream getFlowImgByProcInstId(String procInstId) throws Exception;

    /**
     * 根据流程定义Id获取流程图
     *
     * @param processDefId 流程定义id
     * @return inputStream
     * @throws Exception exception
     */
    InputStream getFlowImgByProcessDefId(String processDefId) throws Exception;
}