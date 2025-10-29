package com.znv.manage.dao.tip;

import com.znv.manage.bean.user.MessagePrecinct;
import com.znv.manage.common.bean.tip.MessageManage;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface MessageManageDao {
    List<Map<String, Object>> queryMessage(@Param(value = "messageId") String messageId,
                                           @Param(value = "messageContent") String messageContent,
                                           @Param(value = "messageTitle") String messageTitle,
                                           @Param(value = "messageType") String messageType,
                                           @Param(value = "sendPrecinctName") String sendPrecinctName,
                                           @Param(value = "receivePerson") String receivePerson,
                                           @Param(value = "receivePrecinctName") String receivePrecinctName);

    List<MessageManage> queryMessage1(@Param(value = "messageId") String messageId);


    void updateMessageType(@Param(value = "Id") String Id,
                           @Param(value = "messageType") String messageType);

    List<Map<String, Object>> queryPrecinct(@Param(value = "roleId") String roleId);

    void saveMessageInfo(MessageManage messageManage);

    void saveImgUrl(@Param(value = "imgUrl") String imgUrl, @Param(value = "messageId") String messageId);

    List<Map<String, Object>> queryMessageInfo(@Param(value = "sendPrecinctName") String sendPrecinctName, @Param(value = "sendPhone") String sendPhone);

    List<Map<String, Object>> queryMessagePrecinct(@Param(value = "messageId") String messageId);

    void deleteMessage(@Param(value = "messageId") String messageId);

    List<MessagePrecinct> queryMessageUser(@Param(value = "precinctName") String precinctName, @Param(value = "roleId") String roleId);
}
