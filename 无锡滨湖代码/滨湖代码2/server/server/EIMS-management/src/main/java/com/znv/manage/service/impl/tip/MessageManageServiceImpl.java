package com.znv.manage.service.impl.tip;

import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.tip.MessageManage;
import com.znv.manage.common.utils.DateUtil;
import com.znv.manage.dao.tip.MessageManageDao;
import com.znv.manage.service.tip.MessageManageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class MessageManageServiceImpl implements MessageManageService {

    @Autowired
    private MessageManageDao messageManageDao;

    @Value("${filePath}")
    private String parentDirPath;

    @Value("${urlPath}")
    private String fileUrlAddress;

    @Override
    public Result saveFiles(MultipartFile[] files) {
        Result result = new Result();
        try {
            List<String> filePath = new ArrayList<>();
            if(null != files && files.length != 0){
                String pre = "/"+DateUtil.getStringDateShort()+"/";
                saveFiles(files,parentDirPath,pre,filePath);
            }
            if(!CollectionUtils.isEmpty(filePath)){
                result.setData(String.join(",",filePath));
            }
        }catch (Exception e){
            result.setCode(1);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    private void saveFiles(MultipartFile[] files, String parentDirPath, String pre, List<String> savePath) {
        for(MultipartFile file:files){
            String fileName = file.getOriginalFilename();
            File parentDir = new File(parentDirPath+pre);
            //如果那个目录不存在先创建目录
            if(!parentDir.exists())
            {
                parentDir.mkdirs();
            }
            try {
                //全局配置文件中配置的目录加上文件名
                File newFile = new File(parentDirPath + pre + fileName);
                String toSavePath = fileUrlAddress + pre + fileName;
                file.transferTo(newFile);
                savePath.add(toSavePath);
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }

    @Override
    public void saveMessageInfo(MessageManage messageManage) {


        messageManageDao.saveMessageInfo(messageManage);
    }

    @Override
    public void saveImgUrl(String imgUrl, String messageId) {
        messageManageDao.saveImgUrl(imgUrl,messageId);
    }
}
