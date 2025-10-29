package com.znv.manage.service.information;

import com.github.pagehelper.PageInfo;
import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.information.DisasterBean;
import com.znv.manage.common.bean.information.GetDisasterBean;

import java.util.List;

/**
 * 灾情信息报送管理
 *
 * @author chd
 * @date 2022/6/7
 */
public interface DisasterManageService {
    /**
     * 新增灾情信息报送
     *
     * @param disasterBean
     * @return
     */
    Result addDisaster(DisasterBean disasterBean);

    /**
     * 编辑灾情信息报送
     *
     * @param disasterBean
     * @return
     */
    Result editDisaster(DisasterBean disasterBean);

    /**
     * 删除灾情信息报送
     *
     * @param id
     * @return
     */
    Result delDisaster(String id);

    /**
     * 灾情信息报送
     *
     * @param id
     * @return
     */
    Result repDisaster(String id);

    /**
     * 查询灾情信息报送
     *
     * @param getDisasterBean
     * @return
     */
    PageInfo<List<DisasterBean>> getDisaster(GetDisasterBean getDisasterBean);

    /**
     * 查询灾情信息报送详情
     *
     * @param id
     * @return
     */
    DisasterBean getDisasterData(String id);
}
