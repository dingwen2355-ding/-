package com.znv.manage.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface HolidayDao {

    public List<String> queryAllHoliday(@Param("type")String type);

    /**
     * 查询节假日
     * @param type 0 : 法定节假日，1 调休日
     * @param year 查询的年分
     * @return
     */
    List<String> queryAllHolidayByYear(@Param("type")String type, @Param("year") String year);

}
