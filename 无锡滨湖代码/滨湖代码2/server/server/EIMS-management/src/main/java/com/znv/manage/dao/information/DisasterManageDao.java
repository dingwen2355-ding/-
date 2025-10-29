package com.znv.manage.dao.information;

import com.znv.manage.common.bean.information.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 灾情信息报送管理
 *
 * @author chd
 * @date 2022/6/7
 */
@Repository
public interface DisasterManageDao {

    /**
     * 新增灾情信息报送
     *
     * @param disasterBean
     */
    void addDisaster(@Param("disasterBean") DisasterBean disasterBean);

    /**
     * 新增突发事件关联
     *
     * @param disasterBean
     */
    void addRelEvent(@Param("disasterBean") DisasterBean disasterBean);

    /**
     * 新增预警信息关联
     *
     * @param disasterBean
     */
    void addRelWarn(@Param("disasterBean") DisasterBean disasterBean);

    /**
     * 添加相关资料信息
     * @param disasterDataBeans
     */
    void addDisasterData(@Param("disasterDataBeans") List<DisasterDataBean> disasterDataBeans);

    /**
     * 编辑灾情信息报送
     *
     * @param disasterBean
     */
    void editDisaster(@Param("disasterBean") DisasterBean disasterBean);

    /**
     * 删除突发事件关联
     *
     * @param disId
     */
    void delRelEvent(@Param("disId") String disId);

    /**
     * 删除预警信息关联
     *
     * @param disId
     */
    void delRelWarn(@Param("disId") String disId);

    /**
     * 删除相关资料关联
     *
     * @param disId
     */
    void delRelData(@Param("disId") String disId);

    /**
     * 删除灾情报送
     *
     * @param disId
     */
    void delDisaster(@Param("disId") String disId);

    /**
     * 灾情信息报送
     *
     * @param disId
     */
    void repDisaster(@Param("disId") String disId);

    /**
     * 获取组
     *
     * @param getDisasterBean
     * @return
     */
    List<DisasterBean> getDisaster(@Param("getDisasterBean") GetDisasterBean getDisasterBean);

    /**
     * 获取组
     *
     * @param id
     * @return
     */
    DisasterBean getDisasterDate(@Param("id") String id);

    /**
     * 获取突发事件
     *
     * @param id
     * @return
     */
    List<EventBean> getEventBean(@Param("id") String id);

    /**
     * 获取相关资料
     *
     * @param id
     * @return
     */
    List<DisasterDataBean> getDate(@Param("id") String id);

    /**
     * 获取预警信息
     *
     * @param id
     * @return
     */
    List<WarnBean> getWarnBean(@Param("id") String id);
}
