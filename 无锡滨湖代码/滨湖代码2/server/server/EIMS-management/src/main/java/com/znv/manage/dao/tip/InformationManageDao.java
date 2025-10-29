package com.znv.manage.dao.tip;

import com.znv.manage.common.bean.tip.InformationBean;
import com.znv.manage.common.bean.tip.QueryInformationBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 资讯管理
 *
 * @author chd
 * @date 2022/8/29
 */
@Repository
public interface InformationManageDao {

    /**
     * 新增资讯
     *
     * @param informationBean
     */
    void add( InformationBean informationBean);

    /**
     * 编辑资讯
     *
     * @param informationBean
     */
    void edit( InformationBean informationBean);

    /**
     * 删除资讯
     *
     * @param ids
     * @return
     */
    void del(@Param(value = "ids") List<String> ids);

    /**
     * 资讯查询
     *
     * @param queryInformationBean
     * @return
     */
    List<InformationBean> query(@Param(value = "queryInformationBean") QueryInformationBean queryInformationBean);

    void updateIsTop(@Param("id") String id);

    String queryAnnexUrl(@Param("id") String id);
}
