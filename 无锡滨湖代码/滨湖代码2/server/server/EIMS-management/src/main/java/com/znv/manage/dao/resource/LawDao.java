package com.znv.manage.dao.resource;

import com.znv.manage.common.bean.resource.Law;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LawDao {
    int deleteLaw(String ids);

    int insertSelective(Law law);

    int updateByPrimaryKeySelective(Law law);

    List<Law> queryLawList(@Param("lawName") String lawName, @Param("accidentType") String accidentType);
}
