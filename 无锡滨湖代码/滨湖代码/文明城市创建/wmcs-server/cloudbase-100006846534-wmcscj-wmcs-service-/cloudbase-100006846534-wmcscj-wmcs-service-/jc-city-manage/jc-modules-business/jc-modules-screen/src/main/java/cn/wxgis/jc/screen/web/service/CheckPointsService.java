package cn.wxgis.jc.screen.web.service;

import cn.wxgis.jc.screen.domain.select.CheckPointsSelect;
import cn.wxgis.jc.screen.domain.select.CheckPointsTypeSelect;
import cn.wxgis.jc.screen.domain.vo.points.CheckPointsTypeVo;
import cn.wxgis.jc.screen.domain.vo.points.CheckPointsVo;
import cn.wxgis.jc.screen.domain.vo.points.CheckTaskVo;
import cn.wxgis.jc.screen.domain.vo.points.CheckUserVo;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

public interface CheckPointsService {

    /**
     * 查询检查点位类型列表
     * @param select
     * @return
     */
    IPage<CheckPointsTypeVo> getCheckPointsTypePage(CheckPointsTypeSelect select);

    /**
     * 查询检查点位列表
     * @param select
     * @return
     */
    IPage<CheckPointsVo> getCheckPointsList(CheckPointsSelect select);


    /**
     * 查询督察原列表
     */
    List<CheckUserVo> getCheckUserList(String pointsId);

    /**
     * 巡查任务列表
     * @return
     */
    List<CheckTaskVo> getCheckTaskList(String pointsTypeId, String pointsId);

    /**
     * 标记
     * @param pointsId
     * @param userId
     */
    int markPoints(String pointsId, String userId, String keyword);
}
