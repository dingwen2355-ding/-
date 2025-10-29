package com.znv.manage.dao.tip;

import com.znv.manage.common.bean.tip.MessagePrecinctBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessagePrecinctBeanDao {
    void saveMessagePrecinctInfo(@Param("list") List<MessagePrecinctBean> list);
    List<MessagePrecinctBean> queryMessagePrecinct(@Param("pMessageId")String pMessageId);
    int countMessage(@Param("receivePerson") String receivePerson);
}
