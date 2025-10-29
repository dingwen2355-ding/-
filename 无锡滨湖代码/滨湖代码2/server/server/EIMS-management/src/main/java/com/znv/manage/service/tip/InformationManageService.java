package com.znv.manage.service.tip;

import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.tip.InformationBean;
import com.znv.manage.common.bean.tip.QueryInformationBean;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 资讯管理
 *
 * @author chd
 * @date 2022/8/29
 */
public interface InformationManageService {

    /**
     * 新增资讯
     *
     * @param informationBean
     * @return
     */
    Result add(InformationBean informationBean,MultipartFile[] files);

    /**
     * 编辑资讯
     *
     * @param informationBean
     * @return
     */
    Result edit(InformationBean informationBean,MultipartFile[] files);

    /**
     * 删除资讯
     *
     * @param ids
     * @return
     */
    Result del(List<String> ids);

    /**
     * 资讯查询
     *
     * @param queryInformationBean
     * @return
     */
    Result query(QueryInformationBean queryInformationBean);

    /**
     * 导入资讯
     *
     * @param file
     * @return
     */
    Result importInformation(MultipartFile file);

    Result updateIsTop(String id);
}
