package com.znv.manage.service.impl.tip;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.znv.manage.common.bean.Result;

import com.znv.manage.common.bean.tip.InformationBean;
import com.znv.manage.common.bean.tip.QueryInformationBean;
import com.znv.manage.common.exception.ResultCodeEnum;
import com.znv.manage.common.utils.CommonUtils;
import com.znv.manage.common.utils.ExcelUtil;
import com.znv.manage.dao.tip.InformationManageDao;
import com.znv.manage.service.tip.InformationManageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 资讯管理
 *
 * @author chd
 * @date 2022/8/29
 */
@Service
@Slf4j
public class InformationManageServiceImpl implements InformationManageService {
    @Resource
    InformationManageDao informationManageDao;

    @Value("${filePath}")
    private String filePath;

    @Value("${urlPath}")
    private String urlPath;

    /**
     * 新增资讯
     *
     * @param informationBean
     * @return
     */
    @Override
    public Result add(InformationBean informationBean, MultipartFile[] files) {
        List<String> tempAnnexUrls = new ArrayList<String>();
        StringBuilder tempAnnexUrlsString = new StringBuilder("");
        Arrays.stream(files).forEach(file -> {
            // 重新上传文件的url
            if (null != files) {
                String s = CommonUtils.uploadFile(file, filePath, urlPath);
                s = URLDecoder.decode(URLEncoder.encode(s));
                tempAnnexUrls.add(s);
                System.out.println("tempAnnexUrlsString.toString().equals(\"\")");
                System.out.println(tempAnnexUrlsString.toString().equals(""));
                if(tempAnnexUrlsString.toString().equals("")){
                    tempAnnexUrlsString.append(s);
                }else{
                    tempAnnexUrlsString.append(","+ s);
                }

            }
        });
        informationBean.setAnnexUrl(tempAnnexUrlsString.toString());

        informationManageDao.add(informationBean);
        // 如果编辑置顶，则更新数据库中其他数据的is_top字段，防止置顶冲突
        if ("1".equals(informationBean.getIsTop())){
            informationManageDao.updateIsTop(informationBean.getId());
        }

        return new Result();
    }

    /**
     * 编辑资讯
     *
     * @param informationBean
     * @return
     */
    @Override
    public Result edit(InformationBean informationBean,MultipartFile[] files) {
        List<String> tempAnnexUrls = new ArrayList<String>();
        StringBuilder tempAnnexUrlsString = new StringBuilder("");
        Arrays.stream(files).forEach(file -> {
            // 重新上传文件的url
            if (null != files) {
                String s = CommonUtils.uploadFile(file, filePath, urlPath);
                s = URLDecoder.decode(URLEncoder.encode(s));
                tempAnnexUrls.add(s);
                System.out.println("tempAnnexUrlsString.toString().equals(\"\")");
                System.out.println(tempAnnexUrlsString.toString().equals(""));
                if(tempAnnexUrlsString.toString().equals("")){
                    tempAnnexUrlsString.append(s);
                }else{
                    tempAnnexUrlsString.append(","+ s);
                }

            }
        });
        // 数据库中的url
        String annexUrl = informationManageDao.queryAnnexUrl(informationBean.getId());
        String[] dbAnnexUrlArr = annexUrl.split(",");


        Arrays.stream(dbAnnexUrlArr).forEach(dbAU ->{
            for(String AnUrl : tempAnnexUrls) {
                if(AnUrl.equals(dbAU)){
                    CommonUtils.deleteFile(dbAU, filePath, urlPath);
                }
            }
        });

        informationBean.setAnnexUrl(tempAnnexUrlsString.toString());

        informationManageDao.edit(informationBean);
        // 如果编辑置顶，则更新数据库中其他数据的is_top字段，防止置顶冲突
        if ("1".equals(informationBean.getIsTop())){
            informationManageDao.updateIsTop(informationBean.getId());
        }

        return new Result();
    }

    /**
     * 删除资讯
     *
     * @param ids
     * @return
     */
    @Override
    public Result del(List<String> ids) {
        Result result = new Result();
        if (CollectionUtils.isEmpty(ids)) {
            log.error("***********************ids is null.***********************");
            result.setCode(500);
            result.setMessage("ids is null.");
            return result;
        }
        for (String id : ids) {
            String annexUrl = informationManageDao.queryAnnexUrl(id);
            CommonUtils.deleteFile(annexUrl, filePath, urlPath);
        }
        informationManageDao.del(ids);
        return result;
    }

    /**
     * 资讯查询
     *
     * @param queryInformationBean
     * @return
     */
    @Override
    public Result query(QueryInformationBean queryInformationBean) {
        if (queryInformationBean == null) {
            queryInformationBean = new QueryInformationBean();
        }
        Integer pageNum = queryInformationBean.getPageNum();
        Integer pageSize = queryInformationBean.getPageSize();
        if (pageNum != null && pageSize != null) {
            PageHelper.startPage(pageNum, pageSize);
        }
        List<InformationBean> informationBeans = informationManageDao.query(queryInformationBean);
        return new Result(new PageInfo(informationBeans));
    }

    /**
     * 导入资讯
     *
     * @param file
     * @return
     */
    @Override
    @Transactional(value = "baseTransactionManager", rollbackFor = Exception.class)
    public Result importInformation(MultipartFile file) {
        Result result = new Result();
        //入参校验
        String name = file.getOriginalFilename();
        if (StringUtils.isEmpty(name)) {
            result.setMessage("文件名为空");
            result.setCode(500);
            return result;
        }
        String ext = name.substring(name.lastIndexOf(".") + 1);
        if (!("xlsx".equals(ext) || "xls".equals(ext))) {
            result.setMessage("不正确的格式");
            result.setCode(500);
            return result;
        }
        Map<String, List<Map<String, String>>> map = ExcelUtil.getResult(file, 1);
        List<Map<String, String>> list1 = map.get("Sheet1");
        List<InformationBean> informationBeans = JSONObject.parseArray(JSON.toJSONString(list1), InformationBean.class);
        if (CollectionUtils.isEmpty(informationBeans)) {
            log.error("informationBeans is null.");
            result.setMessage("文件内容为空");
            result.setCode(500);
            return result;
        }
        for (InformationBean informationBean : informationBeans) {
            informationManageDao.add(informationBean);
        }
        return result;
    }

    @Override
    public Result updateIsTop(String id) {
        Result result = new Result();
        try {
            if (StringUtils.isEmpty(id)) {
                result.setCode(ResultCodeEnum.INVALIDREQUEST.getCode());
                result.setMessage(ResultCodeEnum.INVALIDREQUEST.getName());
                return result;
            }
            informationManageDao.updateIsTop(id);
        } catch (Exception e) {
            log.error(e.toString(), e);
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
        }
        return result;
    }
}
