package com.grandlynn.modules.activiti.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.grandlynn.modules.activiti.domain.vo.ProcessDefVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface IProcessDefinitionService {
    /**
     * 获取流程定义集合
     *
     * @param processDefVO
     * @return Page 分页信息
     */
    public Page<ProcessDefVO> selectProcessDefinitionList(Page<ProcessDefVO> page, ProcessDefVO processDefVO);


    /**
     * 删除流程定义
     *
     * @param id
     * @return
     */
    public int deleteProcessDefinitionById(String id);

    /**
     * 上传并部署流程定义
     *
     * @param file
     * @return
     * @throws IOException
     */
    public void uploadStreamAndDeployment(MultipartFile file) throws IOException;

    /**
     * 启动挂起流程流程定义
     *
     * @param id           流程定义id
     * @param suspendState 流程状态
     * @return
     */
    public void suspendOrActivate(String id, Integer suspendState);

    /**
     * 上传流程流程定义
     *
     * @param multipartFile
     * @return
     */
    public String upload(MultipartFile multipartFile) throws IOException;


}
