package com.znv.manage.service.resource;

import com.github.pagehelper.PageInfo;
import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.resource.Specification;
import org.springframework.web.multipart.MultipartFile;


public interface SpecificationService {
    PageInfo<Specification> querySpecificationList(String fileName, String preparationUnit, Integer pageNum, Integer pageSize);

    Result deleteByPrimaryKey(String ids);

    Result insertSelective(Specification specification, MultipartFile files);

    Result updateByPrimaryKeySelective(Specification specification, MultipartFile files);
}
