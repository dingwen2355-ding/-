package com.znv.manage.dao.event;

import com.znv.manage.common.bean.event.EventTagDictEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author SunJH
 * @description 事件标签字典数据查询Dao
 * @date 2023/1/10
 */
@Repository
public interface EventTagDictDao {

    /**
     * 新增事件标签
     *
     * @param eventTag EventTagDictEntity
     */
    void addEventTag(EventTagDictEntity eventTag);

    /**
     * 查询事件标签列表
     *
     * @param tagId    标签id
     * @param pTagId   父级标签id
     * @param tagLevel 标签级别
     * @param tagName  标签名称
     * @return
     */
    List<EventTagDictEntity> queryEventTagDict(@Param("tagId") String tagId,
                                               @Param("pTagId") String pTagId,
                                               @Param("tagLevel") Integer tagLevel,
                                               @Param("tagName") String tagName);


}
