package com.znv.manage.service.tip;

import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.tip.MessageManage;
import org.springframework.web.multipart.MultipartFile;

public interface MessageManageService {
    Result saveFiles(MultipartFile[] files);
    //Result insertMessage1(MessageManage messageManage, MultipartFile[] files);

    //        void insertMessage1(
//         MessageManage messageManage,
//         List<MessageManage> list ,
//         MultipartFile[] files);
    void saveMessageInfo(MessageManage messageManage);
    void saveImgUrl( String imgUrl, String messageId);
}
