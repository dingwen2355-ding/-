package com.znv.manage.dao.information;

import com.znv.manage.common.bean.information.CommandInformationTemplate;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommandInformationTemplateMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CommandInformationTemplate record);

    int insertSelective(CommandInformationTemplate record);

    CommandInformationTemplate selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CommandInformationTemplate record);

    int updateByPrimaryKey(CommandInformationTemplate record);

    List<CommandInformationTemplate> getTemplate(@Param("id") Long id,
                                                 @Param("title") String title,
                                                 @Param("type1") String type1,
                                                 @Param("type2") String type2);
}