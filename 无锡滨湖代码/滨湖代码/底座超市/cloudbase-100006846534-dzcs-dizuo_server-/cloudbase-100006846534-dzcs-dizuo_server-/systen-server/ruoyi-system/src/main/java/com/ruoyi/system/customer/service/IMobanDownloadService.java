package com.ruoyi.system.customer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.customer.domain.CmMoban;
import com.ruoyi.system.customer.domain.CmMobanDownload;

import java.util.List;
import java.util.Map;


public interface IMobanDownloadService extends IService<CmMobanDownload> {


    List<Map<String, String>> top10Download();

}
