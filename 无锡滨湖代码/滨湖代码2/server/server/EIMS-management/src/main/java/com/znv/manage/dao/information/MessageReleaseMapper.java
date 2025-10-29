package com.znv.manage.dao.information;

import com.znv.manage.common.bean.information.MessageRelease;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageReleaseMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MessageRelease record);

    int updateByPrimaryKey(MessageRelease record);

    List<MessageRelease> getMessage(@Param(value = "title") String title,
                                    @Param(value = "type1") String type1,
                                    @Param(value = "type2") String type2,
                                    @Param(value = "startDate") String startDate,
                                    @Param(value = "endDate") String endDate);

    List<MessageRelease> getMessages(@Param(value = "list") List<String> ids);
}