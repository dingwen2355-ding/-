package com.ruoyi.system.customer.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.customer.domain.CmMoban;
import com.ruoyi.system.customer.domain.CmMobanDownload;
import com.ruoyi.system.customer.mapper.CmMobanDownloadMapper;
import com.ruoyi.system.customer.mapper.CmMobanMapper;
import com.ruoyi.system.customer.mapper.HouseInfoMapper;
import com.ruoyi.system.customer.service.ICmMobanService;
import com.ruoyi.system.customer.service.IMobanDownloadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CmMobanDownloadServiceImpl extends ServiceImpl<CmMobanDownloadMapper, CmMobanDownload> implements IMobanDownloadService {

    @Autowired
    private CmMobanDownloadMapper mobanDownloadMapper;
    @Override
    public List<Map<String, String>> top10Download() {
        return mobanDownloadMapper.top10Download();
    }
}
