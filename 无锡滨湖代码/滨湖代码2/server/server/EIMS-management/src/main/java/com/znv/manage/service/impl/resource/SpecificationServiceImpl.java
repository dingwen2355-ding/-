package com.znv.manage.service.impl.resource;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.resource.Specification;
import com.znv.manage.common.exception.ResultCodeEnum;
import com.znv.manage.common.utils.CommonUtils;
import com.znv.manage.dao.resource.SpecificationDao;
import com.znv.manage.service.resource.SpecificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

@Slf4j
@Service
public class SpecificationServiceImpl implements SpecificationService {
    @Autowired
    private SpecificationDao specificationDao;

    @Value("${filePath}")
    private String filePath;

    @Value("${urlPath}")
    private String urlPath;

    @Override
    public PageInfo<Specification> querySpecificationList(String fileName, String preparationUnit, Integer pageNum, Integer pageSize) {
        if (!ObjectUtils.isEmpty(pageNum) && !ObjectUtils.isEmpty(pageSize)) {
            PageHelper.startPage(pageNum, pageSize);
        }
        List<Specification> list = specificationDao.querySpecificationList(fileName, preparationUnit);
        return new PageInfo<Specification>(list);
    }

    @Override
    public Result deleteByPrimaryKey(String ids) {
        Result result = new Result();
        try {
            if (StringUtils.isEmpty(ids)) {
                result.setCode(ResultCodeEnum.INVALIDREQUEST.getCode());
                result.setMessage(ResultCodeEnum.INVALIDREQUEST.getName());
                return result;
            }
            String[] id = ids.split(",");
            for (int i = 0; i < id.length; i++) {
                String annexUrl = specificationDao.queryAnnexUrl(Long.valueOf(id[i]));
                CommonUtils.deleteFile(annexUrl, filePath, urlPath);
            }
            specificationDao.deleteByPrimaryKey(ids);
        } catch (Exception e) {
            log.error(e.toString(), e);
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
        }
        return result;
    }

    @Override
    public Result insertSelective(Specification specification, MultipartFile files) {
        Result result = new Result();
        try {
            if (null != files) {
                String s = CommonUtils.uploadFile(files, filePath, urlPath);
                s = URLDecoder.decode(URLEncoder.encode(s));
                specification.setAnnexUrl(s);
            }
            specificationDao.insertSelective(specification);
        } catch (Exception e) {
            log.error(e.toString(), e);
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
        }
        return result;
    }

    @Override
    public Result updateByPrimaryKeySelective(Specification specification, MultipartFile files) {
        Result result = new Result();
        try {
            // 重新上传文件的url
            if (null != files) {
                String s = CommonUtils.uploadFile(files, filePath, urlPath);
                s = URLDecoder.decode(URLEncoder.encode(s));
                specification.setAnnexUrl(s);
            }
            // 数据库中的url
            String annexUrl = specificationDao.queryAnnexUrl(specification.getId());
            // 如果两个url不一致且库中的url不为空,则删除
            if (!StringUtils.isEmpty(annexUrl) && !annexUrl.equals(specification.getAnnexUrl())) {
                CommonUtils.deleteFile(annexUrl, filePath, urlPath);
            }
            specificationDao.updateByPrimaryKeySelective(specification);
        } catch (Exception e) {
            log.error(e.toString(), e);
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
        }
        return result;
    }
}
