package com.znv.manage.service.information;

import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.information.MessageRelease;

public interface CommandMessageService {

    Result getMessage(String title, String type1, String type2, String startDate, String endDate);

    Result updateMessage(MessageRelease messageRelease);

    Result insertMessage(MessageRelease messageRelease);

    Result deleteMessage(Long id);

}
