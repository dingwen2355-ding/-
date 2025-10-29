package com.znv.manage.dao.resource;

import com.znv.manage.common.bean.resource.Specification;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpecificationDao {
    List<Specification> querySpecificationList(@Param("fileName") String fileName, @Param("preparationUnit") String preparationUnit);

    int deleteByPrimaryKey(String ids);

    int insertSelective(Specification specification);

    int updateByPrimaryKeySelective(Specification specification);

    String queryAnnexUrl(@Param("id") Long id);

}
